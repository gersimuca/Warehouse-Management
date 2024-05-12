package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.OrderDto;
import com.gersimuca.Warehouse.Management.dto.mapper.OrderDtoMapper;
import com.gersimuca.Warehouse.Management.dto.request.ItemRequest;
import com.gersimuca.Warehouse.Management.dto.request.OrderRequest;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.exception.ItemException;
import com.gersimuca.Warehouse.Management.exception.OrderException;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.exception.UserException;
import com.gersimuca.Warehouse.Management.model.Item;
import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.OrderItem;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.ItemRepository;
import com.gersimuca.Warehouse.Management.repository.OrderItemRepository;
import com.gersimuca.Warehouse.Management.repository.OrderRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.security.provider.JwtService;
import com.gersimuca.Warehouse.Management.util.OrderItemUtil;
import com.gersimuca.Warehouse.Management.util.OrderUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.gersimuca.Warehouse.Management.enumeration.Status.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;
    private final OrderItemRepository orderItemRepository;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @TrackExecutionTime
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void createOrder(List<ItemRequest> itemRequestList, String jwt) {
        try {
            boolean isQuantityAvailable = isQuantityAvailable(itemRequestList);
            if(!isQuantityAvailable) throw new ServiceException("Cannot create order quantity too big");

            String username = jwtService.extractUsername(jwt);
            User user = userRepository.findByUsername(username).orElseThrow(()-> {
                String message = "User not found";
                Throwable cause = new UserException("User not found");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "User " + username + " not found";
                boolean trace = true;
                return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            });

            Order order = OrderUtil.createOrder(Status.CREATED, LocalDate.now().plusDays(7), user);
            order = orderRepository.save(order);

            for(var itemRequest : itemRequestList){
                Item item = itemRepository.findById(itemRequest.getItemId())
                        .orElseThrow(() -> {
                            String message = "Item not found";
                            Throwable cause = new ItemException("Item not found");
                            HttpStatus status = HttpStatus.BAD_REQUEST;
                            String errorDetailMessage = "Item " + itemRequest.getItemId() + " not found";
                            boolean trace = true;

                            return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                        });
                orderItemRepository.save(OrderItemUtil.createOrderItem(item, order, itemRequest.getQuantity() ));
            }
        } catch (Exception e) {
            log.error("Error occurred while creating order: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @TrackExecutionTime
    public void updateOrder(Long id, OrderRequest orderRequest) {
        try {
            Set<Status> set = Set.of(CREATED, DECLINED);
            Order order = orderRepository.findById(id).orElseThrow(()-> {
                String message = "Order not found";
                Throwable cause = new OrderException("Order not found");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "Order " + id + " not found";
                boolean trace = true;
                return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            });

            if(set.contains(orderRequest.getStatus())){
                orderItemRepository.deleteOrderItemsByOrder(order);

                boolean isQuantityAvailable = isQuantityAvailable(orderRequest.getItemRequestList());
                if(!isQuantityAvailable) {
                    String message = "Order quantity too big";
                    Throwable cause = new OrderException("Order quantity too big");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Order " + id + " quantity too big to handle";
                    boolean trace = true;
                    throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                }

                List<Item> items = itemRepository.findItemsByIds(extractItemIds(orderRequest.getItemRequestList()));
                for(var item : items){
                    orderItemRepository.save(OrderItemUtil.createOrderItem(item, order, item.getQuantity()));
                }
                return;
            }
            throw new ServiceException("Order didn't update");
        } catch (Exception e) {
            log.error("Error occurred while updating order: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void cancelOrder(Long id, OrderRequest orderRequest) {
        try {
            Set<Status> set = Set.of(FULFILLED, UNDER_DELIVERY, CANCELED);

            if(!set.contains(orderRequest.getStatus())){
                Order order = orderRepository.findById(id).orElseThrow(()-> new ServiceException("Order not found"));
                order.setStatus(CANCELED);
                orderRepository.save(order);
                return;
            }

            String message = "Order not found";
            Throwable cause = new OrderException("Order under processing");
            HttpStatus status = HttpStatus.CONFLICT;
            String errorDetailMessage = "Order " + orderRequest + " not found";
            boolean trace = true;

            throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
        } catch (Exception e) {
            log.error("Error occurred while canceling order: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void submitOrder(Long id, OrderRequest orderRequest) {
        try {
            Set<Status> set = Set.of(CREATED, DECLINED);
            if(set.contains(orderRequest.getStatus())){
                Order order = orderRepository.findById(id).orElseThrow(()-> {
                    String message = "Order not found";
                    Throwable cause = new OrderException("Order not found");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Order " + id + " not found";
                    boolean trace = true;
                    return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                });

                order.setSubmittedDate(LocalDate.now());
                order.setStatus(AWAITING_APPROVAL);
                orderRepository.save(order);
                return;
            }

            String message = "Order could not be submitted";
            Throwable cause = new OrderException("Order could not be submitted");
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String errorDetailMessage = "Order " + id + " could not be submitted";
            boolean trace = true;

            throw  new ServiceException(message, cause, status, null, errorDetailMessage, trace);
        } catch (Exception e) {
            log.error("Error occurred while submitting order: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public Map<String, Object> getOrdersByStatus(Status status) {
        try {
            List<Order> orders = orderRepository.findByStatusOrderBySubmittedDateDesc(status)
                    .orElseThrow(()-> {
                        String message = "Orders couldn't be found";
                        Throwable cause = new OrderException("Orders couldn't be found");
                        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Orders couldn't be found by status " + status;
                        boolean trace = true;
                        return new ServiceException(message, cause, httpStatus, null, errorDetailMessage, trace);
                    });

            List<OrderDto> orderDtos = orders
                    .stream()
                    .map(order -> new OrderDtoMapper().apply(order))
                    .toList();

            Map<String, Object> data = new HashMap<>(orders.size());
            data.put("orders", List.of(orderDtos));
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting orders by status: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public Map<String, Object> getOrderDetails(Long id) {
        try {
            Map<String,Object> data = new HashMap<>();
            Order order = orderRepository.findById(id).orElseThrow(()-> {
                String message = "Orders couldn't be found";
                Throwable cause = new OrderException("Orders couldn't be found");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "Orders couldn't be found by id " + id;
                boolean trace = true;
                return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            });

            OrderDto orderDto = new OrderDtoMapper().apply(order);
            data.put("order", orderDto);
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting order details: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void approveOrder(Long id) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(id);
            optionalOrder.ifPresent(order -> {
                if (order.getStatus() == Status.AWAITING_APPROVAL) {
                    order.setStatus(Status.APPROVED);
                    orderRepository.save(order);
                }
            });
        } catch (Exception e) {
            log.error("Error occurred while approving order: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void declineOrder(Long orderId, String declineReason) {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            optionalOrder.ifPresent(order -> {
                if (order.getStatus() == Status.AWAITING_APPROVAL) {
                    order.setStatus(Status.DECLINED);
                    order.setDeclineReason(declineReason);
                    orderRepository.save(order);
                }
            });
        } catch (Exception e) {
            log.error("Error occurred while declining order: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void updateOrderToFulfilled(Long id) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> {
                        String message = "Orders couldn't be found";
                        Throwable cause = new OrderException("Orders couldn't be found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Orders couldn't be found by id " + id;
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });
            order.setStatus(Status.FULFILLED);
            orderRepository.save(order);

            for(OrderItem orderItem : order.getOrderItems()){
                int quantiy = itemRepository.findQuantityById(orderItem.getItem().getItemId());
                quantiy -= orderItem.getQuantity();

                Item item = itemRepository.findById(orderItem.getItem().getItemId())
                        .orElseThrow(() -> {
                            String message = "Item couldn't be found";
                            Throwable cause = new ItemException("Item couldn't be found");
                            HttpStatus status = HttpStatus.BAD_REQUEST;
                            String errorDetailMessage = "Item couldn't be found by id " + orderItem.getItem().getItemId();
                            boolean trace = true;
                            return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                        });
                item.setQuantity(quantiy);

                itemRepository.save(item);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating order to fulfilled: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public Map<String, Object> getOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String message = "User not found";
                    Throwable cause = new UserException("User couldn't be found");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "User couldn't be found by username " + username;
                    boolean trace = true;
                    return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                });

        List<Order> orders = orderRepository.findByUser(user)
                .orElseThrow(() -> {
                    String message = "Orders not found";
                    Throwable cause = new OrderException("Orders couldn't be found");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Orders couldn't be found by user " + username;
                    boolean trace = true;
                    return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                });

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDtoMapper().apply(order))
                .toList();

        Map<String, Object> data = new HashMap<>();
        data.put("orders", List.of(orderDtos));
        return data;
    }

    @TrackExecutionTime
    public Map<String, Object> getOrdersByUserByStatus(String username, Status status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String message = "User not found";
                    Throwable cause = new UserException("User couldn't be found");
                    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "User couldn't be found by username " + username;
                    boolean trace = true;
                    return new ServiceException(message, cause, httpStatus, null, errorDetailMessage, trace);
                });
        List<Order> orders = orderRepository.findByUserAndStatus(user, status)
                .orElseThrow(() -> {
                    String message = "Order not found";
                    Throwable cause = new UserException("Order couldn't be found");
                    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Order couldn't be found by username " + username + " and status " + status;
                    boolean trace = true;
                    return new ServiceException(message, cause, httpStatus, null, errorDetailMessage, trace);
                });

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> new OrderDtoMapper().apply(order))
                .toList();

        Map<String, Object> data = new HashMap<>();
        data.put("orders", List.of(orderDtos));
        return data;
    }

    private boolean isQuantityAvailable(List<ItemRequest> itemRequestList){
        for (ItemRequest item : itemRequestList) {
            Integer availableQuantity = itemRepository.findQuantityById(item.getItemId());
            if (availableQuantity == null || item.getQuantity() > availableQuantity) {
                return false;
            }
        }
        return true;
    }

    private List<Long> extractItemIds(List<ItemRequest> items) {
        List<Long> itemIds = new ArrayList<>(items.size());
        for (ItemRequest item : items) {
            itemIds.add(item.getItemId());
        }
        return itemIds;
    }


}

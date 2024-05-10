package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.ItemRequest;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.Item;
import com.gersimuca.Warehouse.Management.model.Order;
import com.gersimuca.Warehouse.Management.model.OrderItem;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.ItemRepository;
import com.gersimuca.Warehouse.Management.repository.OrderItemRepository;
import com.gersimuca.Warehouse.Management.repository.OrderRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.security.provider.JwtService;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;
    private final OrderItemRepository orderItemRepository;

    @TrackExecutionTime
    @Transactional
    public void createOrder(List<ItemRequest> itemRequestList, String jwt){

        boolean isQuantityAvailable = isQuantityAvailable(itemRequestList);
        if(!isQuantityAvailable) throw new ServiceException("Cannot create order quantity too big");

        String username = jwtService.extractUsername(jwt);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ServiceException("User not found"));
        List<Item> items = itemRepository.findItemsByIds(extractItemIds(itemRequestList));

        Order order = Order.builder()
                .submittedDate(LocalDate.now())
                .status(Status.CREATED)
                .deadlineDate(LocalDate.now().plusDays(30))
                .user(user)
                .build();
        order = orderRepository.save(order);

        for(var item : items){
            orderItemRepository.save(OrderItem.builder()
                            .item(item)
                            .order(order)
                            .quantity(item.getQuantity())
                    .build());
        }

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

    public List<Long> extractItemIds(List<ItemRequest> items) {
        List<Long> itemIds = new ArrayList<>(items.size());
        for (ItemRequest item : items) {
            itemIds.add(item.getItemId());
        }
        return itemIds;
    }
}

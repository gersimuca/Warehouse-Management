package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.ScheduleDeliveryRequest;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.exception.DeliveryException;
import com.gersimuca.Warehouse.Management.exception.OrderException;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.exception.TruckException;
import com.gersimuca.Warehouse.Management.model.*;
import com.gersimuca.Warehouse.Management.repository.DeliveryRepository;
import com.gersimuca.Warehouse.Management.repository.OrderRepository;
import com.gersimuca.Warehouse.Management.repository.TruckRepository;
import com.gersimuca.Warehouse.Management.util.DeliveryUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final TruckRepository truckRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TrackExecutionTime
    public void scheduleDelivery(ScheduleDeliveryRequest request) {
        if (request.getDeliveryDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
            logger.error("Deliveries cannot be scheduled on Sundays.");

            String message = "Deliveries cannot be scheduled on Sundays.";
            Throwable cause = new DeliveryException("Deliveries cannot be scheduled");
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String errorDetailMessage = request + " bad request";
            boolean trace = true;

            throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
        }

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    logger.error("Order not found");
                    String message = "Order not found";
                    Throwable cause = new OrderException("Order not found");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Bad request with ID order" + request.getOrderId();
                    boolean trace = true;
                    return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                });

        if (order.getStatus() != Status.APPROVED) {
            logger.error("Only approved orders can be scheduled for delivery.");
            String message = "Order not found";
            Throwable cause = new OrderException("Only approved orders can be scheduled for delivery.");
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String errorDetailMessage = order + " already exists";
            boolean trace = true;
            throw  new ServiceException(message, cause, status, null, errorDetailMessage, trace);
        }

        List<Truck> trucks = truckRepository.findAllByChassisNumberIn(request.getTruckChassisNumbers())
                .orElseThrow(() -> {
                    logger.error("Trucks not found");
                    String message = "Order not found";
                    Throwable cause = new TruckException("Only approved orders can be scheduled for delivery.");
                    HttpStatus status = HttpStatus.BAD_REQUEST;
                    String errorDetailMessage = "Trucks not found with the selected " + request.getTruckChassisNumbers() + " by chassis";
                    boolean trace = true;
                    return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                });

        for (Truck truck : trucks) {
            if (canTruckCompleteDelivery(truck, request.getDeliveryDate(), order)) {

                order.setStatus(Status.UNDER_DELIVERY);

                orderRepository.save(order);

                Delivery delivery = DeliveryUtil.createDelivery(request.getDeliveryDate(), order, truck);
                deliveryRepository.save(delivery);
                return;
            }
            logger.warn("Truck {} is not available or cannot carry the items on {}", truck.getChassisNumber(), request.getDeliveryDate());
        }

        String message = "Trucks can't handle the order";
        Throwable cause = new TruckException("Selected trucks can not handle the loaad");
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorDetailMessage = "Trucks " + trucks + " are not available or cannot carry the items on " + request.getDeliveryDate();
        boolean trace = true;
        throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
    }

    private boolean canTruckCompleteDelivery(Truck truck, LocalDate deliveryDate, Order order) {
        List<Delivery> deliveriesOnDate = deliveryRepository.findAllByTruckAndDeliveryDate(truck, deliveryDate);
        if (!deliveriesOnDate.isEmpty()) {
            return false;
        }

        int totalItems = order.getOrderItems().stream().mapToInt(OrderItem::getQuantity).sum();
        return totalItems <= truck.getCapacity();
    }
}

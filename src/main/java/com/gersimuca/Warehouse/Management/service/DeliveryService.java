package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.ScheduleDeliveryRequest;
import com.gersimuca.Warehouse.Management.enumeration.Status;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.*;
import com.gersimuca.Warehouse.Management.repository.DeliveryRepository;
import com.gersimuca.Warehouse.Management.repository.OrderRepository;
import com.gersimuca.Warehouse.Management.repository.TruckRepository;
import com.gersimuca.Warehouse.Management.util.DeliveryUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

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
            throw new ServiceException("Deliveries cannot be scheduled on Sundays.");
        }

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    logger.error("Order not found");
                    return new ServiceException("Order not found");
                });

        if (order.getStatus() != Status.APPROVED) {
            logger.error("Only approved orders can be scheduled for delivery.");
            throw new ServiceException("Only approved orders can be scheduled for delivery.");
        }

        List<Truck> trucks = truckRepository.findAllByChassisNumberIn(request.getTruckChassisNumbers())
                .orElseThrow(() -> {
                    logger.error("Trucks not found");
                    return new ServiceException("Trucks not found");
                });

        for (Truck truck : trucks) {
            if (canTruckCompleteDelivery(truck, request.getDeliveryDate(), order)) {

                order.setStatus(Status.UNDER_DELIVERY);

                orderRepository.save(order);

                Delivery delivery = DeliveryUtil.createDelivery(request.getDeliveryDate(), order, truck);
                deliveryRepository.save(delivery);
                return;
            }
            logger.error("Truck {} is not available or cannot carry the items on {}", truck.getChassisNumber(), request.getDeliveryDate());
        }

        throw new ServiceException("Trucks " + List.of(trucks) + " are not available or cannot carry the items on " + request.getDeliveryDate());
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

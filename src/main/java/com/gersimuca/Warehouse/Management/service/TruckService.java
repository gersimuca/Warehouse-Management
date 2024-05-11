package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.TruckRequest;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.Truck;
import com.gersimuca.Warehouse.Management.repository.TruckRepository;
import com.gersimuca.Warehouse.Management.util.TruckUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TruckService {

    private final TruckRepository truckRepository;

    private static final Logger log = LoggerFactory.getLogger(TruckService.class);

    @TrackExecutionTime
    public Map<String, Object> getAllTrucks() {
        try {
            List<Truck> trucks = Optional.of(truckRepository.findAll())
                    .orElseThrow(() -> new ServiceException("Trucks not found"));
            Map<String, Object> data = new HashMap<>();
            data.put("trucks", List.of(trucks));
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting all trucks: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public Map<String, Object> getTruckByChassisNumber(String chassisNumber) {
        try {
            Truck truck = truckRepository.findByChassisNumber(chassisNumber)
                    .orElseThrow(() -> new ServiceException("Truck not found"));

            Map<String, Object> data = new HashMap<>();
            data.put("truck", truck);
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting truck by chassis number: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void createTruck(TruckRequest truckRequest) {
        try {
            truckRepository.save(TruckUtil.createTruck(truckRequest.getChassisNumber(), truckRequest.getLicensePlate(), truckRequest.getCapacity()));
        } catch (Exception e) {
            log.error("Error occurred while creating truck: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @TrackExecutionTime
    public void updateTruck(String chassisNumber, TruckRequest truckRequest) {
        try {
            Truck truck = truckRepository.findByChassisNumber(chassisNumber)
                    .orElseThrow(() -> new ServiceException("Truck not found"));
            truckRepository.delete(truck);

            truck = TruckUtil.createTruck(truckRequest.getChassisNumber(), truckRequest.getLicensePlate(), truckRequest.getCapacity());

            truckRepository.save(truck);
        } catch (Exception e) {
            log.error("Error occurred while updating truck: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void deleteTruck(String chassisNumber) {
        try {
            Truck truck = truckRepository.findByChassisNumber(chassisNumber)
                    .orElseThrow(() -> new ServiceException("Truck not found"));
            truckRepository.delete(truck);
        } catch (Exception e) {
            log.error("Error occurred while deleting truck: {}", e.getMessage());
            throw e;
        }
    }
}

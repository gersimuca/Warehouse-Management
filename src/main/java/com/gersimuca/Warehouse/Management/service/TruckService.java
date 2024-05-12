package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.TruckRequest;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.exception.TruckException;
import com.gersimuca.Warehouse.Management.exception.UserException;
import com.gersimuca.Warehouse.Management.model.Truck;
import com.gersimuca.Warehouse.Management.repository.TruckRepository;
import com.gersimuca.Warehouse.Management.util.TruckUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
                    .orElseThrow(() -> {
                        String message = "Trucks not found";
                        Throwable cause = new TruckException("Trucks not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Trucks not found";
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });
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
                    .orElseThrow(() -> {
                        String message = "Truck not found";
                        Throwable cause = new TruckException("Truck not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Truck not found by chassis number " + chassisNumber;
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });

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
            if(truckRequest.getCapacity() > 10) throw new ServiceException("Truck capacity can never exceed 10");
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
                    .orElseThrow(() -> {
                        String message = "Truck not found";
                        Throwable cause = new TruckException("Truck not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Truck not found by chassis number " + chassisNumber;
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });
            truckRepository.delete(truck);

            if(truckRequest.getCapacity() > 10) {
                String message = "Truck cannot exceed capacity of 10";
                Throwable cause = new TruckException("Truck cannot exceed capacity of 10");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "Truck cannot exceed capacity of 10, this truck with chassis:" + chassisNumber;
                boolean trace = true;
                throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            }

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
                    .orElseThrow(() -> {
                        String message = "Truck not found";
                        Throwable cause = new TruckException("Truck not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Truck not found by chassis number " + chassisNumber;
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });
            truckRepository.delete(truck);
        } catch (Exception e) {
            log.error("Error occurred while deleting truck: {}", e.getMessage());
            throw e;
        }
    }
}

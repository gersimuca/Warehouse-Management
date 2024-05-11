package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.ItemRequest;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.Item;
import com.gersimuca.Warehouse.Management.repository.ItemRepository;
import com.gersimuca.Warehouse.Management.util.ItemUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    @TrackExecutionTime
    public Map<String, Object> getAllItems() {
        List<Item> items = Optional.of(itemRepository.findAll())
                .orElseThrow(() -> {
                    logger.error("Items not found");
                    return new ServiceException("Items not found");
                });
        Map<String, Object> data = new HashMap<>();
        data.put("items", List.of(items));
        return data;
    }

    @TrackExecutionTime
    public Map<String, Object> getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Item not found");
                    return new ServiceException("Item not found");
                });

        Map<String, Object> data = new HashMap<>();
        data.put("item", item);
        return data;
    }

    @TrackExecutionTime
    public void createItem(ItemRequest itemRequest) {
        itemRepository.save(ItemUtil.createItem(itemRequest.getName(), itemRequest.getUnitPrice(),itemRequest.getQuantity()));
    }

    @TrackExecutionTime
    public void updateItem(Long id, ItemRequest updatedItem) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Item not found");
                    return new ServiceException("Item not found");
                });

        item.setItemName(updatedItem.getName());
        item.setQuantity(updatedItem.getQuantity());
        item.setUnitPrice(updatedItem.getUnitPrice());

        itemRepository.save(item);
    }

    @TrackExecutionTime
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Item not found");
                    return new ServiceException("Item not found");
                });
        itemRepository.delete(item);
    }
}

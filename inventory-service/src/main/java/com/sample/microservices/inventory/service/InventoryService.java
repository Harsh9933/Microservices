package com.sample.microservices.inventory.service;

import com.sample.microservices.inventory.model.Inventory;
import com.sample.microservices.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public List<Inventory> allItems(){
        return inventoryRepository.findAll();
    }

    @Transactional
    public boolean reserve(String skuCode, Integer quantity) {
        int updated = inventoryRepository.decrementIfEnough(skuCode, quantity);
        return updated > 0;
    }
}

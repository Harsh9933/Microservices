package com.sample.microservices.inventory.controller;

import com.sample.microservices.inventory.model.Inventory;
import com.sample.microservices.inventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @GetMapping("/stock")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> allStock(){
        return inventoryService.allItems();
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@RequestParam String skuCode, @RequestParam Integer quantity) {
        boolean reserved = inventoryService.reserve(skuCode, quantity);
        return reserved ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}


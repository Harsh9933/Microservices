package com.sample.microservices.inventory.repository;

import com.sample.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);

    @Modifying
    @Query(value = "update Inventory i set i.quantity = i.quantity - :quantity where i.skuCode = :skuCode and i.quantity >= :quantity")
    int decrementIfEnough(@Param("skuCode") String skuCode, @Param("quantity") int quantity);
}

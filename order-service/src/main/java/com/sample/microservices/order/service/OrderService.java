package com.sample.microservices.order.service;

import com.sample.microservices.order.client.InventoryClient;
import com.sample.microservices.order.model.Order;
import com.sample.microservices.order.model.OrderRequest;
import com.sample.microservices.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    
    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient){
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }
    
    public void placeOrder(OrderRequest orderRequest) {

        boolean inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (inStock){
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product with skuCode"+ orderRequest.skuCode() + "is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}

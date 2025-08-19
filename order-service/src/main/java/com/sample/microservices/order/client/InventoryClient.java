package com.sample.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;

@FeignClient(value = "inventory", url = "http://localhost:8082")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET , value = "/api/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam Integer quantity);

    @RequestMapping(method = RequestMethod.POST , value = "/api/inventory/reserve")
    ResponseEntity<Void> reserve(@RequestParam String skuCode,@RequestParam Integer quantity);

}

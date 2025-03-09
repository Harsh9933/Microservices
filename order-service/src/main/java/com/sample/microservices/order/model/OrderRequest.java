package com.sample.microservices.order.model;

import java.math.BigDecimal;

public record OrderRequest(String orderNumber, String skuCode, BigDecimal price, Integer quantity) {
}

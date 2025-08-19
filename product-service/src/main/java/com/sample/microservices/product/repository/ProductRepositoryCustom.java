package com.sample.microservices.product.repository;

public interface ProductRepositoryCustom {
    public boolean deleteByStringId(String id);
    public boolean existsByStringId(String id);
}

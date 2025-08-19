package com.sample.microservices.product.repository;

import com.sample.microservices.product.model.Product;
import org.bson.types.ObjectId;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean deleteByStringId(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        Query query = new Query(where("_id").is(objectId));
        var result = mongoTemplate.remove(query, Product.class);
        return result.getDeletedCount() > 0;
    }

    @Override
    public boolean existsByStringId(String id) {
        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        Query query = new Query(Criteria.where("_id").is(objectId));
        return mongoTemplate.exists(query, Product.class);
    }
}



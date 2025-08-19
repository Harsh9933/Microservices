package com.sample.microservices.product.service;

import com.sample.microservices.product.dto.ProductRequest;
import com.sample.microservices.product.dto.ProductResponse;
import com.sample.microservices.product.model.Product;
import com.sample.microservices.product.repository.ProductRepository;
import com.sample.microservices.product.repository.ProductRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductRepositoryCustom productRepositoryCustom;

    public ProductService(ProductRepository productRepository, ProductRepositoryCustom productRepositoryCustom) {
        this.productRepository = productRepository;
        this.productRepositoryCustom = productRepositoryCustom;
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product.Builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);

        return new ProductResponse(product.getId().toString(), product.getName(), product.getDescription(), product.getPrice());
    }

    public Optional<ProductResponse> getProductById(String id){
        return productRepository.findById(id)
                .map(product -> new ProductResponse(product.getId().toString(), product.getName(), product.getDescription(), product.getPrice()));
    }
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId().toString(), product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }

    public boolean deleteProductById(String id) {

        if (!productRepositoryCustom.existsByStringId(id)){
            System.out.println(id);
            return false;
        }
        productRepositoryCustom.deleteByStringId(id);
        return true;
    }
}

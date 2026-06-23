package com.product_api.service;

import com.product_api.model.Product;
import com.product_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product>getProdcts(String category, LocalDateTime cursor,Long cursorId){
        //if cursor not provide then use current time
        if (cursor == null){
            cursor = LocalDateTime.now();
            cursorId= Long.MAX_VALUE;
        }
        return repo.findProducts(
                category,
                cursor,cursorId, PageRequest.of(0,20)
);
}
}
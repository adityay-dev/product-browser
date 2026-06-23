package com.product_api.controller;

import com.product_api.model.Product;
import com.product_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false)String category,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false) Long cursorId){
        LocalDateTime cursorTime = null;
        if (cursor !=null){
            cursorTime = LocalDateTime.parse(cursor);
        }
        return service.getProdcts(category,cursorTime,cursorId);
    }


}

package com.sang.ecommerce.controller;

import com.sang.ecommerce.dto.ProductDTO;
import com.sang.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}

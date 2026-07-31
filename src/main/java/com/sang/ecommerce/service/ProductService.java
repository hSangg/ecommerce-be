package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> getProducts(Pageable pageable);

    ProductDto getProductById(Long id);
}


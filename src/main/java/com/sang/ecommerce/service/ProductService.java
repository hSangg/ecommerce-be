package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDTO> getProducts(Pageable pageable);

    ProductDTO getProductById(Long id);
}


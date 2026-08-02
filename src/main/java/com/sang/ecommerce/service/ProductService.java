package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.ProductDTO;

public interface ProductService {

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);
}


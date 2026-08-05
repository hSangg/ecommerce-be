package com.sang.ecommerce.service;

import com.sang.ecommerce.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> searchProduct(String keyword);
}


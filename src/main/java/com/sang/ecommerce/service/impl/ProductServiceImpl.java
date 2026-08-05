package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.dto.ProductDTO;
import com.sang.ecommerce.exception.AppGlobalException;
import com.sang.ecommerce.exception.ErrorConstant;
import com.sang.ecommerce.repository.ProductDocumentRepository;
import com.sang.ecommerce.repository.ProductRepository;
import com.sang.ecommerce.service.ProductService;
import com.sang.ecommerce.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductDocumentRepository productDocumentRepository;

    @Override
    @Cacheable(value = "products", key = "#id")
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        log.debug("query db");
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() ->
                        new AppGlobalException(ErrorConstant.PRODUCT_NOT_FOUND_MSG, ErrorConstant.PRODUCT_NOT_FOUND_CODE, HttpStatus.NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductDTO createProduct(ProductDTO productDTO) {
        var productEntity = productMapper.toEntity(productDTO);
        var savedProduct = productRepository.save(productEntity);

        var productDocument = productMapper.toDocument(savedProduct);
        productDocumentRepository.save(productDocument);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public List<ProductDTO> searchProduct(String keyword) {
        return productDocumentRepository
                .findByNameContainingOrDescriptionContaining(keyword, keyword)
                .stream().map(productMapper::toDTOFromDocument)
                .toList();
    }
}


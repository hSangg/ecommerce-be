package com.sang.ecommerce.service.mapper;

import com.sang.ecommerce.dto.ProductDTO;
import com.sang.ecommerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseEntity<ProductDTO, Product> {
}


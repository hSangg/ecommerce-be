package com.sang.ecommerce.mapper;

import com.sang.ecommerce.dto.ProductDto;
import com.sang.ecommerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);
}


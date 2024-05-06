package com.ecommerce.product.dtos;

import com.ecommerce.product.entities.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductListDTO {
    private List<ProductListItemDTO> items;
    private long totalElements;
}

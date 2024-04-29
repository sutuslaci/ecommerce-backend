package com.ecommerce.product.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsDTO {
    private String id;
    private String name;
    private double price;
    private String description;
    private int stock;
    private String imageURL;
}

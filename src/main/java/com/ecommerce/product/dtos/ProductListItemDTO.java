package com.ecommerce.product.dtos;

import com.ecommerce.product.entities.Product;
import lombok.Data;

@Data
public class ProductListItemDTO {
    private String id;
    private String url;
    private String name;
    private double price;
    private String imageURL;

    public static ProductListItemDTO fromEntity(Product product) {
        var item = new ProductListItemDTO();
        item.id = product.getId();
        item.url = product.getHumanReadableURL();
        item.name = product.getName();
        item.price = product.getPrice();
        item.imageURL = product.getImageURL();
        return item;
    }
}

package com.ecommerce.product;

import com.ecommerce.auth.utils.ProductUtil;
import com.ecommerce.product.dtos.ProductDetailsDTO;
import com.ecommerce.product.dtos.ProductListDTO;
import com.ecommerce.product.dtos.ProductListItemDTO;
import com.ecommerce.product.entities.Product;
import com.ecommerce.product.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @PostConstruct
    public void init() {
        List<Product> products = new ArrayList<>();

        // Add 30 products
        for (int i = 1; i <= 30; i++) {
            String name = this.generateUniqueName(i);
            String humanReadableURL = ProductUtil.generateHumanReadableURL(name);
            String description = "Description of the " + name;
            double price = Math.round((Math.random() * 1000) + 100); // Random price between 100 and 1100
            int quantity = (int) (Math.random() * 50); // Random quantity between 0 and 50
            String imageUrl = ""; // Set your image URL here if applicable

            Product product = new Product(UUID.randomUUID().toString(), humanReadableURL, name, description, price, quantity, imageUrl);
            products.add(product);
        }

        productRepository.saveAll(products);
    }

    // Only for testing.
    private String generateUniqueName(int index) {
        List<String> adjectives = Arrays.asList("Luxurious", "Sleek", "Stylish", "Premium", "Elegant", "Modern", "High-end", "Innovative", "Sophisticated", "Exquisite");
        List<String> nouns = Arrays.asList("Watch", "Headphones", "Backpack", "Laptop", "Camera", "Speaker", "Smartphone", "Gaming Chair", "Coffee Maker", "Sneakers");
        String adjective = adjectives.get((int) (Math.random() * adjectives.size()));
        String noun = nouns.get((int) (Math.random() * nouns.size()));
        return adjective + " " + noun + " " + index;
    }

    public ProductListDTO getProducts(int pageNumber, int pageSize) {
        Page<Product> productPage =  productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<ProductListItemDTO> products = productPage.stream()
                .filter(product -> product.getStock() > 0)
                .map(ProductListItemDTO::fromEntity).toList();
        return ProductListDTO.builder()
                .items(products)
                .totalElements(productPage.getTotalElements())
                .build();
    }

    public ProductDetailsDTO getProductDetails(String url) {
        Product product = productRepository.findByHumanReadableURL(url)
                .orElseThrow();

        return ProductDetailsDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .imageURL(product.getImageURL())
                .price(product.getPrice())
                .build();
    }
}

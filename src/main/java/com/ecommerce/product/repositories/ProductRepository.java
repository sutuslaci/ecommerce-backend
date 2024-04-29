package com.ecommerce.product.repositories;

import com.ecommerce.product.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByPrice(double price, Pageable pageable);
    Optional<Product> findByHumanReadableURL(String url);
}

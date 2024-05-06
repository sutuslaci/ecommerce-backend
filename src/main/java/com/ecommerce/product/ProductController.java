package com.ecommerce.product;

import com.ecommerce.product.dtos.ProductDetailsDTO;
import com.ecommerce.product.dtos.ProductListDTO;
import com.ecommerce.product.dtos.ProductListItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<ProductListDTO> getProducts(@RequestParam int pageNumber, @RequestParam int pageSize) {
        ProductListDTO products = productService.getProducts(pageNumber, pageSize);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/details")
    public ResponseEntity<ProductDetailsDTO> getDetails(@RequestParam String url) {
        ProductDetailsDTO product = productService.getProductDetails(url);
        return ResponseEntity.ok(product);
    }
}

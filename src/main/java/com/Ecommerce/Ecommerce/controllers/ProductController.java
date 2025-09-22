package com.Ecommerce.Ecommerce.controllers;

import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.exception.InvalidCredentialsException;
import com.Ecommerce.Ecommerce.service.interf.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product APIs")
public class ProductController {

    private final ProductService productService;
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/crete")
    public ResponseEntity<Response>creteProduct(
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image ,
            @RequestParam   String name,
            @RequestParam String description ,
            @RequestParam BigDecimal price){
        if (categoryId==null || image.isEmpty() || name.isEmpty() || description.isEmpty() || price == null){
            throw new InvalidCredentialsException("All fields are required");
        }
        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/update/{productId}")
    public ResponseEntity<Response>updateProduct(
            @RequestParam Long productId,
            @RequestParam (required = false) Long categoryId,
            @RequestParam (required = false) MultipartFile image ,
            @RequestParam  (required = false) String name,
            @RequestParam (required = false) String description ,
            @RequestParam (required = false) BigDecimal price){
        return ResponseEntity.ok(productService.updateProduct
                (productId,categoryId,image,name,description,price));
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/delete/{productId}")
    public ResponseEntity<Response>deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/get-all-product-id/{productId}")
    public ResponseEntity<Response>getProductsById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response>getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-by-category-id/{categoryId}")
    public ResponseEntity<Response>getProductsByCategory(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<Response>searchForProducts(@PathVariable String searchValue){
        return ResponseEntity.ok(productService.searchProduct(searchValue));
    }

}

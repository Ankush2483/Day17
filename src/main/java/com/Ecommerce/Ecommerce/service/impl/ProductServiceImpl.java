package com.Ecommerce.Ecommerce.service.impl;

import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.entity.Category;
import com.Ecommerce.Ecommerce.entity.Product;
import com.Ecommerce.Ecommerce.exception.NotFoundException;
import com.Ecommerce.Ecommerce.mapper.EntityDtoMapper;
import com.Ecommerce.Ecommerce.repositories.CategoryRepo;
import com.Ecommerce.Ecommerce.repositories.ProductRepo;
import com.Ecommerce.Ecommerce.service.interf.AwsS3Service;
import com.Ecommerce.Ecommerce.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;
    private final AwsS3Service awsS3Service;

    @Override
    public Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new NotFoundException("category not found"));
        String productImageUrl= awsS3Service.saveImageToS3(image);
        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepo.save(product);
        return Response.builder()
                .status(200)
                .message("product successfully created")
                .build();
    }
    @Override
    public Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description, BigDecimal price) {
        return null;
    }

    @Override
    public Response deleteProduct(Long productId) {
        return null;
    }

    @Override
    public Response getProductById(Long productId) {
        return null;
    }

    @Override
    public Response getAllProducts() {
        return null;
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        return null;
    }

    @Override
    public Response searchProduct(String searchValue) {
        return null;
    }


}

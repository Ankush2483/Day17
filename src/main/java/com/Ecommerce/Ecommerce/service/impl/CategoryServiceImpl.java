package com.Ecommerce.Ecommerce.service.impl;

import com.Ecommerce.Ecommerce.DTOs.CategoryDto;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.entity.Category;
import com.Ecommerce.Ecommerce.exception.NotFoundException;
import com.Ecommerce.Ecommerce.mapper.EntityDtoMapper;
import com.Ecommerce.Ecommerce.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl {
    private final CategoryRepo categoryRepo;
    private final EntityDtoMapper entityDtoMapper;

    public Response createCategory(CategoryDto categoryRequest) {
         Category category = new Category();
         category.setName(categoryRequest.getName());
         categoryRepo.save(category);
         return Response.builder()
                 .status(200)
                 .message("category created successfully")
                 .build();
    }
        public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new
                NotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepo.save(category);
        return Response.builder()
                .status(200)
                .message("category update successfully")
                .build();
    }
    public Response getAllCategories() {
        List<Category>categories= categoryRepo.findAll();
        List<CategoryDto>categoryDtoList= categories.stream()
                .map(entityDtoMapper::mapCategoryToDtoBasic)
                .toList();
        return Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();

    }
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()
                ->new NotFoundException("category not found"));
       categoryRepo.delete(category);
        return Response.builder()
                .status(200)
                .message("category was delete successfully")
                .build();
    }
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()
                ->new NotFoundException("category not found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToDtoBasic(category);
        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }
}

package com.Ecommerce.Ecommerce.service.interf;


import com.Ecommerce.Ecommerce.DTOs.CategoryDto;
import com.Ecommerce.Ecommerce.DTOs.Response;
import com.Ecommerce.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryService{
    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}

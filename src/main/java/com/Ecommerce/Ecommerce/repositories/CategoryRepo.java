package com.Ecommerce.Ecommerce.repositories;

import com.Ecommerce.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}

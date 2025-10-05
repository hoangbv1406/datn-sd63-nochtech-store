package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;

public interface CategoryService {
    Category createCategory(CategoryDTO category);
}

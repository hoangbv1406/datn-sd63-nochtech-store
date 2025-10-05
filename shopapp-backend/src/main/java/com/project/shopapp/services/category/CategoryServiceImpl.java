package com.project.shopapp.services.category;

import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.Category;
import com.project.shopapp.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newCategory = Category.builder().name(categoryDTO.getName()).build();
        return categoryRepository.save(newCategory);
    }

}

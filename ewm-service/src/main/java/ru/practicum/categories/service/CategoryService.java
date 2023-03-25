package ru.practicum.categories.service;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(PostCategoryDto postCategoryDto);

    void deleteCategory(long catId);

    CategoryDto changeCategory(long catId, PostCategoryDto postCategoryDto);

    List<CategoryDto> getAllCategories(int from, int size);

    CategoryDto getCategoryById(long catId);
}

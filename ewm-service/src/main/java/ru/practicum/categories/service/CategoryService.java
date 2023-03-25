package ru.practicum.categories.service;

import ru.practicum.categories.dto.GetCategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;

import java.util.List;

public interface CategoryService {
    GetCategoryDto createNewCategory(PostCategoryDto postCategoryDto);

    void deleteCategory(long catId);

    GetCategoryDto changeCategory(long catId, PostCategoryDto postCategoryDto);

    List<GetCategoryDto> getAllCategories(int from, int size);

    GetCategoryDto getCategoryById(long catId);
}

package ru.practicum.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exceptions.EwmServiceNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto createNewCategory(PostCategoryDto postCategoryDto) {
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository
                .save(categoryMapper.mapPostCategoryDtoToCategoryModel(postCategoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto changeCategory(long catId, PostCategoryDto postCategoryDto) {
        getCategoryById(catId);
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository
                .save(categoryMapper.mapPostCategoryDtoToCategoryModel(postCategoryDto)));
    }

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        Pageable pageble = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        return categoryRepository.findAll(pageble).stream()
                .map(categoryMapper::mapCategoryModelToGetCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository.findById(catId).orElseThrow(() ->
                new EwmServiceNotFound(String.format("Category with id=%d was not found", catId))));
    }
}

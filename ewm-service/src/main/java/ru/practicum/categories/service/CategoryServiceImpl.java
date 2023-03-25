package ru.practicum.categories.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.GetCategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.exceptions.EwmServiceNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public GetCategoryDto createNewCategory(PostCategoryDto postCategoryDto) {
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository
                .save(categoryMapper.mapPostCategoryDtoToCategoryModel(postCategoryDto)));
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    public GetCategoryDto changeCategory(long catId, PostCategoryDto postCategoryDto) {
        getCategoryById(catId);
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository
                .save(categoryMapper.mapPostCategoryDtoToCategoryModel(postCategoryDto)));
    }

    @Override
    public List<GetCategoryDto> getAllCategories(int from, int size) {
        Pageable pageble = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        return categoryRepository.findAll(pageble).stream()
                .map(categoryMapper::mapCategoryModelToGetCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public GetCategoryDto getCategoryById(long catId) {
        return categoryMapper.mapCategoryModelToGetCategoryDto(categoryRepository.findById(catId).orElseThrow(() ->
                new EwmServiceNotFound(String.format("Category with id=%d was not found", catId))));
    }
}

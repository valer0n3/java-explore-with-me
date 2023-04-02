package ru.practicum.categories.mapper;

import org.mapstruct.Mapper;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;
import ru.practicum.categories.model.CategoryModel;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto mapCategoryModelToGetCategoryDto(CategoryModel categoryModel);

    CategoryModel mapPostCategoryDtoToCategoryModel(PostCategoryDto postCategoryDto);
}

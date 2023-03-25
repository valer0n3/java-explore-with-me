package ru.practicum.categories.mapper;

import org.mapstruct.Mapper;
import ru.practicum.categories.dto.GetCategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;
import ru.practicum.categories.model.CategoryModel;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    GetCategoryDto mapCategoryModelToGetCategoryDto(CategoryModel categoryModel);

    CategoryModel mapGetCategoryDtoToCategoryModel(GetCategoryDto getCategoryDto);

    PostCategoryDto mapCategoryModelToPostCategoryDto(CategoryModel categoryModel);

    CategoryModel mapPostCategoryDtoToCategoryModel(PostCategoryDto postCategoryDto);
}

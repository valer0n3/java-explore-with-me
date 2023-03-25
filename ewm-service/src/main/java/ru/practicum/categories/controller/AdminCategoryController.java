package ru.practicum.categories.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.categories.dto.GetCategoryDto;
import ru.practicum.categories.dto.PostCategoryDto;
import ru.practicum.categories.service.CategoryServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/categories")
@AllArgsConstructor
public class AdminCategoryController {
    private final CategoryServiceImpl categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetCategoryDto createNewCategory(@Valid @RequestBody PostCategoryDto postCategoryDto) {
        return categoryService.createNewCategory(postCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") long catId) {
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public GetCategoryDto changeCategory(@PathVariable("catId") long catId,
                                         @Valid @RequestBody PostCategoryDto postCategoryDto) {
        return categoryService.changeCategory(catId, postCategoryDto);
    }
}

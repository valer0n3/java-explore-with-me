package ru.practicum.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
}

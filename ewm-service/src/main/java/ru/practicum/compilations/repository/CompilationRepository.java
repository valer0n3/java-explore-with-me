package ru.practicum.compilations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilations.model.CompilationModel;

public interface CompilationRepository extends JpaRepository<CompilationModel, Long> {
}

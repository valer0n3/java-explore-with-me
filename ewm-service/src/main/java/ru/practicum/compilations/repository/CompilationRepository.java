package ru.practicum.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.compilations.model.CompilationModel;

import java.util.List;

public interface CompilationRepository extends JpaRepository<CompilationModel, Long> {
    @Query("SELECT i FROM CompilationModel i WHERE i.pinned = ?1 OR ?1 IS NULL")
    List<CompilationModel> findCompilationsByFilter(Boolean pinned, Pageable page);
}

package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto saveNewCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto);

    List<CompilationDto> getAllCompilations(Boolean pinned,
                                            Integer from,
                                            Integer size);

    CompilationDto getCompilation(Long compId);
}
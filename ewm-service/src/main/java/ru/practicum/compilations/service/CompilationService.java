package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto saveNewCompilation(NewCompilationDto newCompilationDto);


    void deleteCompilation(Long compId);

    public CompilationDto updateCompilation(NewCompilationDto newCompilationDto);

    public List<CompilationDto> getAllCompilations(Boolean pinned,
                                                   Integer from,
                                                   Integer size);

    public CompilationDto getCompilation(Long compId);
}
package ru.practicum.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

import java.util.List;
@Service
@AllArgsConstructor
public class CompilationServiceImpl implements CompilationService{
    @Override
    public CompilationDto saveNewCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public void deleteCompilation(Long compId) {
    }

    @Override
    public CompilationDto updateCompilation(NewCompilationDto newCompilationDto) {
        return null;
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return null;
    }
}

package ru.practicum.compilations.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@AllArgsConstructor
@Validated
public class CompilationPublicController {
    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAllCompilations(@RequestParam(name = "pinned", defaultValue = "true") Boolean pinned,
                                                   @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                   @RequestParam(name = "size", defaultValue = "10") Integer size) {
        System.out.println("****** compilations");
        return compilationService.getAllCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") Long compId) {
        return compilationService.getCompilation(compId);

    }
}

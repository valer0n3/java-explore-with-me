package ru.practicum.compilations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.model.CompilationModel;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.EventModel;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
  @Mapping(target = "events", source = "eventsModel")
    CompilationModel mapNewCompilationDtoToCompilationModel(NewCompilationDto newCompilationDto, List<EventModel> eventsModel);

  @Mapping(target = "events", source = "eventsShortDto")
  CompilationDto mapCompilationModelToCompilationDto(CompilationModel compilationModel, List<EventShortDto> eventsShortDto);
}

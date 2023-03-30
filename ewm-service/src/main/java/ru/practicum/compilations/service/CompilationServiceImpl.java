package ru.practicum.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.compilations.model.CompilationModel;
import ru.practicum.compilations.repository.CompilationRepository;
import ru.practicum.controller.StatClientController;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.EventModel;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.service.EventServiceImpl;
import ru.practicum.requests.model.EventIdAndAmountOfConfirmedRequestsModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {
    private static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.of(2000, 01, 01, 00, 00, 00);
    private static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.of(2099, 01, 01, 01, 00, 00);
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;
    private final EventServiceImpl eventServiceImpl;
    private final StatClientController statClientController;

    @Override
    @Transactional
    public CompilationDto saveNewCompilation(NewCompilationDto newCompilationDto) {
        List<EventModel> eventModels = new ArrayList<>();
        if (newCompilationDto.getEvents() != null || newCompilationDto.getEvents().isEmpty()) {
            eventModels = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        }
        CompilationModel compilationModel = compilationRepository.save(compilationMapper
                .mapNewCompilationDtoToCompilationModel(newCompilationDto, eventModels));
        //TODO add views and amountOfconfiremedrequests
        List<EventIdAndAmountOfConfirmedRequestsModel> listOfAmountConfirmedRequestsForEachEvent = eventServiceImpl
                .getListOfParticipants(newCompilationDto.getEvents());
        Map<Long, Long> mapOfConfirmedRequests = eventServiceImpl
                .getMapOfEventsAndAmountOfConfirmedRequests(listOfAmountConfirmedRequestsForEachEvent);
        List<EventShortDto> eventShortDtos = eventServiceImpl.getEventShortDtoFromEventModel(eventModels);
        Map<Long, Long> mapOfEventsIdToAmountOfViews = statClientController.getStatistic(DEFAULT_START_DATE,
                DEFAULT_END_DATE, newCompilationDto.getEvents(), null);
        eventServiceImpl.addAmountOFConfirmedRequestsAndViews(eventShortDtos, mapOfConfirmedRequests, mapOfEventsIdToAmountOfViews);
        return compilationMapper.mapCompilationModelToCompilationDto(compilationModel, eventShortDtos);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
    }

    @Override
    @Transactional
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

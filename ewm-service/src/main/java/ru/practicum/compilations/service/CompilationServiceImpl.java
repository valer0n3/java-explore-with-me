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
import ru.practicum.exceptions.EwmServiceNotFound;
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
        if (newCompilationDto.getEvents() != null || !newCompilationDto.getEvents().isEmpty()) {
            eventModels = findAllEventsModel(newCompilationDto.getEvents());
        }
        CompilationModel compilationModel = compilationRepository.save(compilationMapper
                .mapNewCompilationDtoToCompilationModel(newCompilationDto, eventModels));
        return CreateCompilationDtoWithAmountOfConfirmedRequsestsAndAMountOfViews(eventModels,
                newCompilationDto.getEvents(),
                compilationModel);
    }

    private List<EventModel> findAllEventsModel(List<Long> events) {
        return eventRepository.findAllByIdIn(events);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        CompilationModel compilationModel = findCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto) {
        CompilationModel compilationModel = findCompilationById(compId);
        List<EventModel> eventModels = new ArrayList<>();
        if (newCompilationDto.getEvents() != null) {
            eventModels = findAllEventsModel(newCompilationDto.getEvents());
            compilationModel.setEvents(eventModels);
        }
        if (newCompilationDto.getPinned() != null) {
            compilationModel.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getTitle() != null) {
            compilationModel.setTitle(newCompilationDto.getTitle());
        }
        compilationRepository.save(compilationModel);
        return CreateCompilationDtoWithAmountOfConfirmedRequsestsAndAMountOfViews(eventModels,
                newCompilationDto.getEvents(),
                compilationModel);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        return null;
    }

    private CompilationModel findCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new EwmServiceNotFound(String
                .format("Compilation id: %s was not found", compId)));
    }

    private CompilationDto CreateCompilationDtoWithAmountOfConfirmedRequsestsAndAMountOfViews(List<EventModel> eventModels, List<Long> events, CompilationModel compilationModel) {
        List<EventIdAndAmountOfConfirmedRequestsModel> listOfAmountConfirmedRequestsForEachEvent = eventServiceImpl
                .getListOfParticipants(events);
        Map<Long, Long> mapOfConfirmedRequests = eventServiceImpl
                .getMapOfEventsAndAmountOfConfirmedRequests(listOfAmountConfirmedRequestsForEachEvent);
        List<EventShortDto> eventShortDtos = eventServiceImpl.getEventShortDtoFromEventModel(eventModels);
        Map<Long, Long> mapOfEventsIdToAmountOfViews = statClientController.getStatistic(DEFAULT_START_DATE,
                DEFAULT_END_DATE, events, null);
        eventServiceImpl.addAmountOFConfirmedRequestsAndViews(eventShortDtos, mapOfConfirmedRequests, mapOfEventsIdToAmountOfViews);
        return compilationMapper.mapCompilationModelToCompilationDto(compilationModel, eventShortDtos);
    }
}

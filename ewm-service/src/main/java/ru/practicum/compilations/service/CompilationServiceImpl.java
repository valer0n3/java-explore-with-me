package ru.practicum.compilations.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.Collectors;

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
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
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
        Pageable pageble = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        List<CompilationModel> compilationModel = compilationRepository.findCompilationsByFilter(pinned, pageble);
        List<CompilationDto> compilationDtos = compilationModel.stream().map(compilationModel1 -> {
            List<EventShortDto> eventShortDtos = eventServiceImpl.getEventShortDtoFromEventModel(compilationModel1.getEvents());
            return compilationMapper.mapCompilationModelToCompilationDto(compilationModel1, eventShortDtos);
        }).collect(Collectors.toList());


/*        List<EventModel> listOfAllEvents = new ArrayList<>();
        compilationModel.forEach(compilationModel1 -> listOfAllEvents.addAll(compilationModel1.getEvents()));
        List<Long> listOfEventIds = listOfAllEvents.stream()
                .map(EventModel::getId).collect(Collectors.toList());
        List<EventIdAndAmountOfConfirmedRequestsModel> listOfAmountConfirmedRequestsForEachEvent = eventServiceImpl
                .getListOfParticipants(listOfEventIds);
        Map<Long, Long> mapOfConfirmedRequests = eventServiceImpl
                .getMapOfEventsAndAmountOfConfirmedRequests(listOfAmountConfirmedRequestsForEachEvent);
        List<EventShortDto> eventShortDtos = eventServiceImpl.getEventShortDtoFromEventModel(listOfAllEvents);
        Map<Long, Long> mapOfEventsIdToAmountOfViews = statClientController.getStatistic(DEFAULT_START_DATE,
                DEFAULT_END_DATE, listOfEventIds, null);
        eventServiceImpl.addAmountOFConfirmedRequestsAndViews(eventShortDtos, mapOfConfirmedRequests, mapOfEventsIdToAmountOfViews);*/
        //пройтись циклом по всем compilatinsModel и собрать compilationModel + List<EventShortDtos>
        return compilationDtos;//compilationMapper.mapCompilationModelToCompilationDto(compilationModel, eventShortDtos);
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        CompilationModel compilationModel = findCompilationById(compId);
        List<Long> eventIds = compilationModel.getEvents().stream()
                .map(EventModel::getId).collect(Collectors.toList());
        return CreateCompilationDtoWithAmountOfConfirmedRequsestsAndAMountOfViews(compilationModel.getEvents(),
                eventIds,
                compilationModel);
    }

    private CompilationModel findCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new EwmServiceNotFound(String
                .format("Compilation id: %s was not found", compId)));
    }

    private CompilationDto CreateCompilationDtoWithAmountOfConfirmedRequsestsAndAMountOfViews(
            List<EventModel> eventModels, List<Long> events, CompilationModel compilationModel) {
        List<EventIdAndAmountOfConfirmedRequestsModel> listOfAmountConfirmedRequestsForEachEvent = eventServiceImpl
                .getListOfParticipants(events);
        Map<Long, Long> mapOfConfirmedRequests = eventServiceImpl
                .getMapOfEventsAndAmountOfConfirmedRequests(listOfAmountConfirmedRequestsForEachEvent);
        List<EventShortDto> eventShortDtos = eventServiceImpl.getEventShortDtoFromEventModel(eventModels);
        if (eventModels.isEmpty()) {
            eventServiceImpl.addAmountOFConfirmedRequests(eventShortDtos, mapOfConfirmedRequests);
            return compilationMapper.mapCompilationModelToCompilationDto(compilationModel, eventShortDtos);
        }
        Map<Long, Long> mapOfEventsIdToAmountOfViews = statClientController.getStatistic(DEFAULT_START_DATE,
                DEFAULT_END_DATE, events, null);
        eventServiceImpl.addAmountOFConfirmedRequestsAndViews(eventShortDtos, mapOfConfirmedRequests, mapOfEventsIdToAmountOfViews);
        return compilationMapper.mapCompilationModelToCompilationDto(compilationModel, eventShortDtos);
    }
}

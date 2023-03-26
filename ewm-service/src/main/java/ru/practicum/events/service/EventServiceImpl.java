package ru.practicum.events.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.model.CategoryModel;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.events.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.events.dto.UpdateEventAdminAndUserRequestDTO;
import ru.practicum.events.enums.EventStateEnum;
import ru.practicum.events.enums.StateActionEnum;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.mapper.LocationMapper;
import ru.practicum.events.model.EventModel;
import ru.practicum.events.model.LocationModel;
import ru.practicum.events.repository.EventRepository;
import ru.practicum.events.repository.LocationRepository;
import ru.practicum.exceptions.EwmServiceForbiddenException;
import ru.practicum.exceptions.EwmServiceNotFound;
import ru.practicum.users.model.UserModel;
import ru.practicum.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getAllEventsForCurrentUser(long userId, int from, int size) {
        Pageable pageble = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        checkIfUserExists(userId);
        return eventRepository.findByInitiatorId(userId, pageble).stream()
                .map(eventMapper::mapEventModelToEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto createNewEvent(long userId, NewEventDto newEventDto) {
        checkIfEventDateIs2HoursLaterThanCreationDate(newEventDto.getEventDate());
        CategoryModel categoryModel = checkIfCategoryExists(newEventDto.getCategory());
        UserModel userModel = checkIfUserExists(userId);
        LocationModel locationModel = checkIfLocationExistsIfNotThenSave(newEventDto.getLocation());
        EventModel eventModel = eventMapper.mapNewEventDtoToEventModel(newEventDto);
        eventModel.setCategory(categoryModel);
        eventModel.setInitiator(userModel);
        eventModel.setLocation(locationModel);
        eventModel.setCreatedOn(LocalDateTime.now());
        eventModel.setState(EventStateEnum.PENDING.toString());
        return eventMapper.mapEventModelToEventFullDto(eventRepository.save(eventModel));
    }

    @Override
    public EventFullDto getEventByIdForCurrentUser(long userId, long eventId) {
        checkIfUserExists(userId);
        return eventMapper.mapEventModelToEventFullDto(eventRepository.findAllByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Event with id=%d was not found", eventId))));
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, long eventId,
                                    UpdateEventAdminAndUserRequestDTO updateEventUserRequestDto) {
        checkIfUserExists(userId);
        EventModel eventModelToBeChanged = eventRepository.findAllByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Event with id=%d was not found", eventId)));
        checkIfEvenStatusIsCanceledOrPending(eventModelToBeChanged);
        updateEventModuleStatePrivate(updateEventUserRequestDto, eventModelToBeChanged);
        return eventMapper.mapEventModelToEventFullDto(eventRepository
                .save(updateEventModule(updateEventUserRequestDto, eventModelToBeChanged)));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsForCurrentUser(long userId, long eventId) {
        return null;
    }

    @Override
    public EventRequestStatusUpdateResultDto updateRequestStatusForCurrentUser(long userId, long eventId, EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequestDto) {
        return null;
    }

    @Override
    public List<EventFullDto> getAllEventsAdmin(List<Long> users, List<String> state, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        Pageable pageble = PageRequest.of(from / size, size /*Sort.by("start").descending()*/);
        //TODO transfer to another method
        if (users.isEmpty()) {
            users = null;
        }
        if (state.isEmpty()) {
            state = null;
        }
        if (categories.isEmpty()) {
            categories = null;
        }
        return eventRepository.getAllEventsAdmin(users, state, categories, rangeStart, rangeEnd, pageble).stream()
                .map(eventMapper::mapEventModelToEventFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEventAndStatusAdmin(long eventId, UpdateEventAdminAndUserRequestDTO updateEventAdminAndUserRequestDTO) {
        EventModel eventModelToUpdate = checkIfEventExists(eventId);


    /*

        checkIfEvenStatusIsCanceledOrPending(eventModelToBeChanged);
        updateEventModuleStatePrivate(updateEventUserRequestDto, eventModelToBeChanged);
        return eventMapper.mapEventModelToEventFullDto(eventRepository
                .save(updateEventModule(updateEventUserRequestDto, eventModelToBeChanged)));*/
    }

    private EventModel checkIfEventExists(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Event with id=%d was not found", eventId)));
    }

    @Override
    public List<EventShortDto> getEventsWithFilter(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventWithFilterById(long id) {
        return null;
    }

    private void checkIfEventDateIs2HoursLaterThanCreationDate(LocalDateTime localDateTime) {
        if (localDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EwmServiceForbiddenException(String.format("Field: eventDate." +
                    " Error: должно содержать дату, которая еще не наступила. Value: %s", localDateTime));
        }
    }

    private CategoryModel checkIfCategoryExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Category with id: %d is not existed", categoryId)));
    }

    private UserModel checkIfUserExists(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EwmServiceNotFound(String
                .format("User with id: %d is not existed", userId)));
    }

    //TODO change to Optional as IDEA suggests
    private LocationModel checkIfLocationExistsIfNotThenSave(LocationDto locationDto) {
        LocationModel locationModel = locationRepository.findByLatAndLon(locationDto.getLat(),
                locationDto.getLon());
        if (locationModel == null) {
            return locationRepository.save(locationMapper.mapLocationDtoToLocationModel(locationDto));
        } else {
            return locationModel;
        }
    }

    private void checkIfEvenStatusIsCanceledOrPending(EventModel updatedEventModel) {
        if ((updatedEventModel.getState().toUpperCase().equals(EventStateEnum.CANCELED.toString())) ||
                (updatedEventModel.getState().toUpperCase().equals(EventStateEnum.PENDING.toString()))) {
        } else {
            throw new EwmServiceForbiddenException("Only pending or canceled events can be changed");
        }
    }

    private EventModel updateEventModule(UpdateEventAdminAndUserRequestDTO updateData,
                                         EventModel eventModel) {
        if (updateData.getAnnotation() != null) {
            eventModel.setAnnotation(updateData.getAnnotation());
        }
        if (updateData.getCategory() != null) {
            eventModel.setCategory(checkIfCategoryExists(updateData.getCategory()));
        }
        if (updateData.getDescription() != null) {
            eventModel.setDescription(updateData.getDescription());
        }
        if (updateData.getEventDate() != null) {
            checkIfEventDateIs2HoursLaterThanCreationDate(updateData.getEventDate());
            eventModel.setEventDate(updateData.getEventDate());
        }
        if (updateData.getLocation() != null) {
            eventModel.setLocation(checkIfLocationExistsIfNotThenSave(updateData.getLocation()));
        }
        if (updateData.getPaid() != null) {
            eventModel.setPaid(updateData.getPaid());
        }
        if (updateData.getParticipantLimit() != null) {
            eventModel.setParticipantLimit(updateData.getParticipantLimit());
        }
        if (updateData.getRequestModeration() != null) {
            eventModel.setRequestModeration(updateData.getRequestModeration());
        }
        if (updateData.getTitle() != null) {
            eventModel.setTitle(updateData.getTitle());
        }
        return eventModel;
    }

    private void updateEventModuleStatePrivate(UpdateEventAdminAndUserRequestDTO updateData,
                                               EventModel eventModel) {
        if (updateData.getStateAction() != null) {
            StateActionEnum.checkIfUpdatedStateActionEnumIsCorrect(updateData.getStateAction());
            updateStateAndPublishedDatePrivate(eventModel, updateData.getStateAction());
        }
    }

    private void updateStateAndPublishedDatePrivate(EventModel eventModel, StateActionEnum stateAction) {
        if (stateAction == StateActionEnum.SEND_TO_REVIEW) {
            eventModel.setState(EventStateEnum.PENDING.toString());
        }
        if (stateAction == StateActionEnum.CANCEL_REVIEW) {
            eventModel.setState(EventStateEnum.CANCELED.toString());
        }
    }
}




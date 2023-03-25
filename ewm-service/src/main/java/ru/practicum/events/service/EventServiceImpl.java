package ru.practicum.events.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.categories.model.CategoryModel;
import ru.practicum.categories.repository.CategoryRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.events.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.dto.ParticipationRequestDto;
import ru.practicum.events.dto.UpdateEventAdminAndUserRequestDTO;
import ru.practicum.events.enums.EventStateEnum;
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

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getAllEventsForCurrentUser(long userId, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto createNewEvent(long userId, NewEventDto newEventDto) {
        checkIfEventDateIs2HoursLaterThanCreationDate(newEventDto);
        CategoryModel categoryModel = checkIfCategoryExists(newEventDto);
        UserModel userModel = checkIfUserExists(userId);
        LocationModel locationModel = checkIfLocationExistsIfNotThenSave(newEventDto);
        EventModel eventModel = eventMapper.mapNewEventDtoToEventModel(newEventDto);
        eventModel.setCategory(categoryModel);
        eventModel.setInitiator(userModel);
        eventModel.setLocation(locationModel);
        eventModel.setCreatedOn(LocalDateTime.now());
        eventModel.setState(EventStateEnum.PENDING.toString());
        return eventMapper.mapEventModelToEventFullDto(eventRepository.save(eventModel));
    }

    private void checkIfEventDateIs2HoursLaterThanCreationDate(NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EwmServiceForbiddenException(String.format("Field: eventDate." +
                    " Error: должно содержать дату, которая еще не наступила. Value: %s", newEventDto.getEventDate()));
        }
    }

    private CategoryModel checkIfCategoryExists(NewEventDto newEventDto) {
        return categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new EwmServiceNotFound(String
                        .format("Category with id: %d is not existed", newEventDto.getCategory())));
    }

    private UserModel checkIfUserExists(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EwmServiceNotFound(String
                .format("User with id: %d is not existed", userId)));
    }

    private LocationModel checkIfLocationExistsIfNotThenSave(NewEventDto newEventDto) {
        LocationModel locationModel = locationRepository.findByLatAndLon(newEventDto.getLocation().getLat(),
                newEventDto.getLocation().getLon());
        if (locationModel == null) {
            return locationRepository.save(locationMapper.mapLocationDtoToLocationModel(newEventDto.getLocation()));
        } else {
            return locationModel;
        }
    }

    @Override
    public EventFullDto getEventByIdForCurrentUser(long userId, long eventId) {
        return null;
    }

    @Override
    public EventFullDto updateEvent(long userId, long eventId, UpdateEventAdminAndUserRequestDTO updateEventUserRequestDto) {
        return null;
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
    public List<EventFullDto> getAllEvents(List<Integer> users, List<String> state, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto updateEventAndStatus(long eventId, UpdateEventAdminAndUserRequestDTO updateEventAdminAndUserRequestDTO) {
        return null;
    }

    @Override
    public List<EventShortDto> getEventsWithFilter(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        return null;
    }

    @Override
    public EventFullDto getEventWithFilterById(long id) {
        return null;
    }
}

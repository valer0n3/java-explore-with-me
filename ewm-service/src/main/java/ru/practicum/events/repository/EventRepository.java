package ru.practicum.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.events.model.EventModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventModel, Long> {
    List<EventModel> findByInitiatorId(long userId, Pageable pageable);

    Optional<EventModel> findAllByInitiatorIdAndId(long userId, long eventId);

    @Query("SELECT i FROM EventModel i WHERE i.eventDate BETWEEN ?4 and ?5 " +
            "AND (i.initiator.id IN ?1 OR ?1 IS NULL) " +
            "AND (i.state IN ?2 OR ?2 IS NULL) " +
            "AND (i.category.id IN ?3 OR ?3 IS NULL)")
    List<EventModel> getAllEventsAdmin(List<Long> users,
                                       List<String> state,
                                       List<Long> categories,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       Pageable pageable);
}

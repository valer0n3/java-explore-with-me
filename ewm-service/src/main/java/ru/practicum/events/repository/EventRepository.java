package ru.practicum.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.EventModel;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventModel, Long> {
    List<EventModel> findByInitiatorId(long userId, Pageable pageable);

    Optional<EventModel> findAllByInitiatorIdAndId(long userId, long eventId);
}

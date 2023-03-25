package ru.practicum.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.EventModel;

public interface EventRepository extends JpaRepository<EventModel, Long> {
}

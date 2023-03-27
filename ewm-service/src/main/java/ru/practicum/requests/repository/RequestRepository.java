package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.requests.model.RequestModel;

public interface RequestRepository extends JpaRepository<RequestModel, Long> {
    // Optional<RequestModel> findByRequesterIdAndEventId(Long requestorid, Long eventId);

    @Query("Select COUNT(req) FROM RequestModel req " +
            "WHERE req.event.id = ?1 AND req.status = upper(?2)")
    int getAmountOfParticipants(long eventId, String status);
}

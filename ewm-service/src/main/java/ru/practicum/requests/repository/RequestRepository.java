package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.requests.model.EventIdAndAmountOfConfirmedRequestsModel;
import ru.practicum.requests.model.RequestModel;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestModel, Long> {
    // Optional<RequestModel> findByRequesterIdAndEventId(Long requestorid, Long eventId);

    @Query("Select COUNT(req) FROM RequestModel req " +
            "WHERE req.event.id = ?1 AND req.status = upper(?2)")
    int getAmountOfParticipants(long eventId, String status);

    @Query("SELECT new ru.practicum.requests.model.EventIdAndAmountOfConfirmedRequestsModel(i.event.id, count(i.event)) " +
            "FROM RequestModel i WHERE i.event.id in ?1 AND i.status = upper(?2) " +
            "GROUP BY i.event")
    List<EventIdAndAmountOfConfirmedRequestsModel> getListOfAmountParticipants(List<Long> eventIds, String status);

    Optional<RequestModel> findByIdAndRequesterId(Long requestId, Long userId);

    List<RequestModel> findAllByEventIdAndEventId(Long requesterId, Long eventId);

    List<RequestModel> findAllByRequesterId(Long userId);

    List<RequestModel> findAllByIdIn(List<Long> listOfIds);
}

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

    /*     @Query("SELECT i FROM EventModel i WHERE (i.eventDate >= ?1) AND (i.eventDate <= ?2 OR i.eventDate IS NULL)")
        List<EventModel> findEventByCustomFilter(String text,
                                                 List<Long> categories,
                                                 Boolean paid,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 Pageable pageable);
    */
    @Query("SELECT i FROM EventModel i WHERE (i.eventDate >= ?1) AND (i.eventDate <= ?2) " +
            "AND upper(i.state) = upper(?3) " +
            "AND (i.category.id IN ?5 OR ?5 IS NULL) " +
            "AND (i.paid = ?6)" +
            "AND ((upper (i.annotation) like upper(concat('%', ?4, '%'))) OR ?4 IS NULL)  " +
            "OR ((upper (i.description) like upper(concat('%', ?4, '%'))) OR ?4 IS NULL)" +
            "ORDER BY i.eventDate")
    List<EventModel> findEventByCustomFilter(LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             String eventStatePublished,
                                             String searchTest,
                                             List<Long> categories,
                                             Boolean paid,
                                             Pageable pageable);

    List<EventModel> findAllByIdIn(List<Long> eventIds);

      /*   "where upper(i.name) like upper(concat('%', ?1, '%')) " + "" +
                 "or upper(i.description) like upper(concat('%', ?1, '%'))" +*/
}
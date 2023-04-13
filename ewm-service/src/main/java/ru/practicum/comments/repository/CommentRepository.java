package ru.practicum.comments.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.comments.model.CommentModel;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentModel, Long> {
    void deleteAllByEventId(Long eventId);

    List<CommentModel> getAllByEventId(Long eventId, Pageable pageable);
}

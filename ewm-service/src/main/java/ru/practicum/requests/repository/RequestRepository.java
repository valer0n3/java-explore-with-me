package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.RequestModel;

public interface RequestRepository extends JpaRepository<RequestModel, Long> {
}

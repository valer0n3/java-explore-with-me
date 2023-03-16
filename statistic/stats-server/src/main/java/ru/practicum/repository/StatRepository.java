package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.StatModel;

public interface StatRepository extends JpaRepository<StatModel, Long> {
}

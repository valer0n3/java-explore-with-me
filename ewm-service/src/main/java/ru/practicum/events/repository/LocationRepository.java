package ru.practicum.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.LocationModel;

public interface LocationRepository extends JpaRepository<LocationModel, Long> {
    LocationModel findByLatAndLon(float latitude, float longitude);
}

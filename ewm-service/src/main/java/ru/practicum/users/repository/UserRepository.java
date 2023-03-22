package ru.practicum.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}

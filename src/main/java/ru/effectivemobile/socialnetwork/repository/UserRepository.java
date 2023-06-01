package ru.effectivemobile.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effectivemobile.socialnetwork.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    boolean existsByUserNameOrEmail(String username,String email);

}

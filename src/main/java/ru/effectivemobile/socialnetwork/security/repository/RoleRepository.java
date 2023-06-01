package ru.effectivemobile.socialnetwork.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effectivemobile.socialnetwork.security.model.ERole;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole,Long> {

    Optional<UserRole> findByRole(ERole role);

}

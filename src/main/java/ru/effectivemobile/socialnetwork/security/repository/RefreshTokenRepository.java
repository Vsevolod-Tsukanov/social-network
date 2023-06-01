package ru.effectivemobile.socialnetwork.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effectivemobile.socialnetwork.security.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);
}

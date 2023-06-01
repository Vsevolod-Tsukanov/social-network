package ru.effectivemobile.socialnetwork.security.dto.response;

import ru.effectivemobile.socialnetwork.security.model.ERole;

public record JwtResponse(String accessToken, String refreshToken, String userName, String email, ERole role) {
    public static final String TYPE = "Bearer";
}

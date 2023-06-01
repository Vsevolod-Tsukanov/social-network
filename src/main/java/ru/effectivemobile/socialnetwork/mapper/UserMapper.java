package ru.effectivemobile.socialnetwork.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.effectivemobile.socialnetwork.security.dto.request.SignupRequest;
import ru.effectivemobile.socialnetwork.security.dto.response.UserResponse;
import ru.effectivemobile.socialnetwork.model.User;
import ru.effectivemobile.socialnetwork.security.model.ERole;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder encoder;

    public User toNewUser(SignupRequest request, ERole role) {
        User user = new User();
        user.setUserName(request.username());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setRole(role);

        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getUserName(), user.getEmail());
    }

}

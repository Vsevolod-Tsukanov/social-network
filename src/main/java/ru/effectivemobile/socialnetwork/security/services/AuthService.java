package ru.effectivemobile.socialnetwork.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.effectivemobile.socialnetwork.security.dto.request.SigninRequest;
import ru.effectivemobile.socialnetwork.security.dto.request.SignupRequest;
import ru.effectivemobile.socialnetwork.mapper.UserMapper;
import ru.effectivemobile.socialnetwork.model.User;
import ru.effectivemobile.socialnetwork.security.dto.response.JwtResponse;
import ru.effectivemobile.socialnetwork.security.dto.response.NewTokensResponse;
import ru.effectivemobile.socialnetwork.security.dto.request.UpdateTokenRequest;
import ru.effectivemobile.socialnetwork.security.jwt.JwtUtils;
import ru.effectivemobile.socialnetwork.security.model.RefreshToken;
import ru.effectivemobile.socialnetwork.repository.UserRepository;
import ru.effectivemobile.socialnetwork.security.model.ERole;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public void register(SignupRequest request) {

        if (userRepository.existsByUserNameOrEmail(request.username(), request.email())){
            throw new RuntimeException("User with given username or email already exist");
        }

        User entity = userMapper.toNewUser(request, ERole.ROLE_USER);

        userRepository.save(entity);
    }

    public JwtResponse authenticate(SigninRequest request){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateJwtToken(userDetails);

        ERole role  = userDetails.getRole();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return new JwtResponse(accessToken,refreshToken.getToken(),userDetails.getUsername(),userDetails.getEmail(),role);
    }

    public NewTokensResponse updateToken(UpdateTokenRequest request){
        String requestRefreshToken = request.refreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
                    return new NewTokensResponse(token, requestRefreshToken);
                })

                .orElseThrow(() -> new RuntimeException("Refresh token is not in database"));
    }
}

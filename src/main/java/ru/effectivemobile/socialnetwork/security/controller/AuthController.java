package ru.effectivemobile.socialnetwork.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effectivemobile.socialnetwork.security.dto.request.SigninRequest;
import ru.effectivemobile.socialnetwork.security.dto.request.SignupRequest;
import ru.effectivemobile.socialnetwork.security.dto.response.JwtResponse;
import ru.effectivemobile.socialnetwork.security.dto.response.MessageResponse;
import ru.effectivemobile.socialnetwork.security.dto.response.NewTokensResponse;
import ru.effectivemobile.socialnetwork.security.dto.request.UpdateTokenRequest;
import ru.effectivemobile.socialnetwork.security.services.AuthService;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody SigninRequest request){
        return authService.authenticate(request);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody SignupRequest request){
        authService.register(request);
        return ResponseEntity.ok(new MessageResponse("Registered successfully"));
    }

    @PostMapping("/updateToken")
    public NewTokensResponse updateToken(@RequestBody UpdateTokenRequest request){
        return authService.updateToken(request);
    }

}

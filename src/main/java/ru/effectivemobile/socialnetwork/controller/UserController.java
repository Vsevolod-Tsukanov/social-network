package ru.effectivemobile.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.effectivemobile.socialnetwork.dto.response.FriendsResponse;
import ru.effectivemobile.socialnetwork.dto.response.UsernamesResponse;
import ru.effectivemobile.socialnetwork.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/friends")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/request")
    @PreAuthorize("hasRole('role_user')")
    public FriendsResponse sendFriendRequest(@RequestParam("username") String username) {
        return userService.sendFriendRequest(username);
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('USER')")
    public UsernamesResponse getFriendRequests() {
        return userService.getFriendRequests();
    }

    @PostMapping("/accept")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UsernamesResponse addToFriendList(@RequestParam("username") String username) {
        return userService.addToFriendsList(username);
    }

}

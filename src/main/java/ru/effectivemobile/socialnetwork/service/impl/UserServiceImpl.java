package ru.effectivemobile.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.effectivemobile.socialnetwork.dto.response.UsernamesResponse;
import ru.effectivemobile.socialnetwork.dto.response.FriendsResponse;
import ru.effectivemobile.socialnetwork.model.User;
import ru.effectivemobile.socialnetwork.repository.UserRepository;
import ru.effectivemobile.socialnetwork.security.services.UserDetailsImpl;
import ru.effectivemobile.socialnetwork.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public FriendsResponse sendFriendRequest(String username) {
        var userForRequest = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User with given username does not exist"));

        var currentUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var currentUser = userRepository.findByUserName(currentUserDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("CurrentUser with given username does not exist"));

        userForRequest.getFollowers().add(currentUser);
        userForRequest.getFriendsRequests().add(currentUser);

        return new FriendsResponse(currentUser.getUserName(), userForRequest.getUserName());
    }


    @Override
    public UsernamesResponse getFriendRequests() {
        var currentUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var currentUser = userRepository.findByUserName(currentUserDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("CurrentUser with given username does not exist"));

        return new UsernamesResponse(currentUser
                .getFriendsRequests().stream()
                .map(User::getUserName)
                .collect(Collectors.toList()));
    }

    @Override
    public UsernamesResponse addToFriendsList(String username) {
        var currentUserDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var currentUser = userRepository.findByUserName(currentUserDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("CurrentUser with given username does not exist"));
        var userToAccept = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User with given username does not exist"));

        List<User> friendsRequests = currentUser.getFriendsRequests();
        if (!friendsRequests.contains(userToAccept)){
            throw new RuntimeException("User with the given username did not send a friend request");
        }else {
            friendsRequests.remove(userToAccept);
            currentUser.getFriends().add(userToAccept);
        }

        return new UsernamesResponse(currentUser
                .getFriends().stream()
                .map(User::getUserName)
                .collect(Collectors.toList()));

    }

    @Override
    public void sendRequestToMessage() {

    }

    @Override
    public void getMessagesRequests() {

    }
}

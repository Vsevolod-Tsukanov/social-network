package ru.effectivemobile.socialnetwork.service;

import ru.effectivemobile.socialnetwork.dto.response.UsernamesResponse;
import ru.effectivemobile.socialnetwork.dto.response.FriendsResponse;

public interface UserService {

    FriendsResponse sendFriendRequest(String username);

    UsernamesResponse getFriendRequests();

    UsernamesResponse addToFriendsList(String username);

    void sendRequestToMessage();
    void getMessagesRequests();
}

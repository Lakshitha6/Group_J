package com.groupJ.socialmedia_app.service;

import com.groupJ.socialmedia_app.entity.FriendRequest;
import com.groupJ.socialmedia_app.entity.User;
import java.util.List;

public interface FriendService {
    List<User> getAllUsersExcept(String currentUserEmail);
    void sendFriendRequest(Long receiverId, String senderEmail);
    List<FriendRequest> getIncomingRequests(String userEmail);
    List<FriendRequest> getOutgoingRequests(String userEmail);
    void acceptRequest(Long requestId);
    void declineRequest(Long requestId);

}
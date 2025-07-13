package com.groupJ.socialmedia_app.service.impl;

import com.groupJ.socialmedia_app.entity.FriendRequest;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.repository.FriendRequestRepository;
import com.groupJ.socialmedia_app.repository.UserRepository;
import com.groupJ.socialmedia_app.service.FriendService;
import com.groupJ.socialmedia_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Override
    public List<User> getAllUsersExcept(String currentUserEmail) {
        User currentUser = userRepository.findByEmail(currentUserEmail).orElseThrow(()-> new RuntimeException("User not found"));

        //Get all users and remove ourself that already logged in
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(user -> user.getId().equals(currentUser.getId()));

        //Remove users that already sent or receive friend requests
        allUsers.removeIf(user -> friendRequestRepository.existsBySenderAndReceiver(currentUser,user)
                || friendRequestRepository.existsBySenderAndReceiver(user,currentUser));

        return allUsers;
    }

    @Override
    public void sendFriendRequest(Long receiverId, String senderEmail) {
        User sender = userRepository.findByEmail(senderEmail).orElseThrow(()-> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getId().equals(receiver.getId())) throw new RuntimeException("Cannot send friend request to yourself");

        boolean exists = friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
        if (exists) throw new RuntimeException("Request already sent");

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(FriendRequest.RequestStatus.PENDING);

        friendRequestRepository.save(friendRequest);

    }

    @Override
    public List<FriendRequest> getIncomingRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return friendRequestRepository.findByReceiverAndStatus(user, FriendRequest.RequestStatus.PENDING);
    }

    @Override
    public List<FriendRequest> getOutgoingRequests(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return friendRequestRepository.findBySender(user);
    }

    @Override
    public void acceptRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(FriendRequest.RequestStatus.ACCEPTED);
        friendRequestRepository.save(request);
    }

    @Override
    public void declineRequest(Long requestId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(FriendRequest.RequestStatus.DECLINED);
        friendRequestRepository.save(request);
    }

    @Override
    public List<User> getAcceptedFriends(String userEmail) {
        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        List<FriendRequest> acceptedRequests = friendRequestRepository.findAll().stream()
                .filter(friendRequest -> friendRequest.getStatus() == FriendRequest.RequestStatus.ACCEPTED &&
                        (friendRequest.getSender().equals(currentUser) || friendRequest.getReceiver().equals(currentUser)))
                .toList();

        List<User> friends =  new ArrayList<>();
        for (FriendRequest request: acceptedRequests) {
            if (request.getSender().equals(currentUser)) {
                friends.add(request.getReceiver());
            } else {
                friends.add(request.getSender());
            }
        }
        return friends;
    }
}

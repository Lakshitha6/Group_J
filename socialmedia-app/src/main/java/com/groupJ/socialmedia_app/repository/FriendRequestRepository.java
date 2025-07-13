package com.groupJ.socialmedia_app.repository;

import com.groupJ.socialmedia_app.entity.FriendRequest;
import com.groupJ.socialmedia_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByReceiverAndStatus(User receiver, FriendRequest.RequestStatus status);

    List<FriendRequest> findBySender(User sender);

    boolean existsBySenderAndReceiver(User sender, User receiver);

    boolean existsBySenderAndReceiverOrReceiverAndSender(User sender, User receiver, User receiver2, User sender2);
}

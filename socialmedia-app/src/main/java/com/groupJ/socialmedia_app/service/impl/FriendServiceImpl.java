import com.groupJ.socialmedia_app.entity.FriendRequest;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.repository.FriendRequestRepository;
import com.groupJ.socialmedia_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Autowired
private FriendRequestRepository friendRequestRepo;

@Autowired
private UserRepository userRepository;

@Override
public void sendFriendRequest(Long receiverId, String senderEmail) {
    User sender = userRepository.findByEmail(senderEmail)
            .orElseThrow(() -> new RuntimeException("Sender not found"));
    User receiver = userRepository.findById(receiverId)
            .orElseThrow(() -> new RuntimeException("Receiver not found"));

    if (sender.getId().equals(receiver.getId())) throw new RuntimeException("Cannot send request to yourself");

    boolean exists = friendRequestRepo.existsBySenderAndReceiver(sender, receiver);
    if (exists) throw new RuntimeException("Request already sent");

    FriendRequest request = new FriendRequest();
    request.setSender(sender);
    request.setReceiver(receiver);
    request.setStatus(FriendRequest.RequestStatus.PENDING);
    friendRequestRepo.save(request);
}

@Override
public List<FriendRequest> getIncomingRequests(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    return friendRequestRepo.findByReceiverAndStatus(user, FriendRequest.RequestStatus.PENDING);
}

@Override
public List<FriendRequest> getOutgoingRequests(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    return friendRequestRepo.findBySender(user);
}

@Override
public void acceptRequest(Long requestId) {
    FriendRequest request = friendRequestRepo.findById(requestId).orElseThrow();
    request.setStatus(FriendRequest.RequestStatus.ACCEPTED);
    friendRequestRepo.save(request);
}

@Override
public void declineRequest(Long requestId) {
    FriendRequest request = friendRequestRepo.findById(requestId).orElseThrow();
    request.setStatus(FriendRequest.RequestStatus.DECLINED);
    friendRequestRepo.save(request);
}

package com.groupJ.socialmedia_app.controller;

import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/users")
    public String userDirectory(Model model, Principal principal) {
        String currentEmail = principal.getName();
        List<User> users = friendService.getAllUsersExcept(currentEmail);
        model.addAttribute("users", users);
        return "user-directory";
    }
    @PostMapping("/friend/request/{receiverId}")
    public String sendRequest(@PathVariable Long receiverId, Principal principal) {
        try {
            friendService.sendFriendRequest(receiverId, principal.getName());
        } catch (Exception e) {
            // Optional: Add error flash message
        }
        return "redirect:/users";
    }

    @GetMapping("/friend/requests")
    public String viewRequests(Model model, Principal principal) {
        String email = principal.getName();
        model.addAttribute("incoming", friendService.getIncomingRequests(email));
        model.addAttribute("outgoing", friendService.getOutgoingRequests(email));
        return "friend-requests";
    }

    @PostMapping("/friend/accept/{id}")
    public String acceptRequest(@PathVariable Long id) {
        friendService.acceptRequest(id);
        return "redirect:/friend/requests";
    }

    @PostMapping("/friend/decline/{id}")
    public String declineRequest(@PathVariable Long id) {
        friendService.declineRequest(id);
        return "redirect:/friend/requests";
    }

}

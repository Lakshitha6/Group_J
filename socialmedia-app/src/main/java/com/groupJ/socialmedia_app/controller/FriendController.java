package com.groupJ.socialmedia_app.controller;

import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}

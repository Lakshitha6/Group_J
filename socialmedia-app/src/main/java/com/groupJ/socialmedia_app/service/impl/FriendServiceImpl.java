package com.groupJ.socialmedia_app.service.impl;

import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.repository.UserRepository;
import com.groupJ.socialmedia_app.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsersExcept(String currentUserEmail) {
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .toList();
    }
}
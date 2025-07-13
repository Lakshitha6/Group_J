package com.groupJ.socialmedia_app.service;

import com.groupJ.socialmedia_app.entity.User;
import java.util.List;

public interface FriendService {
    List<User> getAllUsersExcept(String currentUserEmail);
}
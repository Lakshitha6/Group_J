package com.groupJ.socialmedia_app.service;

import com.groupJ.socialmedia_app.dto.PostDTO;
import com.groupJ.socialmedia_app.entity.Post;
import com.groupJ.socialmedia_app.entity.User;

import java.util.List;

public interface PostService {
    void createPost(PostDTO postDTO, String userEmail);
    List<Post> getPostByUser(String userEmail);
    List<Post> getPostsByUsers(List<User> users);
}

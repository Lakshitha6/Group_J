package com.groupJ.socialmedia_app.service;

import com.groupJ.socialmedia_app.dto.PostDTO;
import com.groupJ.socialmedia_app.entity.Post;

import java.util.List;

public interface PostService {
    void createPost(PostDTO postDTO, String userEmail);
    List<Post> getPostsByUser(String userEmail);
}

package com.groupJ.socialmedia_app.service.impl;

import com.groupJ.socialmedia_app.dto.PostDTO;
import com.groupJ.socialmedia_app.entity.Post;
import com.groupJ.socialmedia_app.entity.User;
import com.groupJ.socialmedia_app.repository.PostRepository;
import com.groupJ.socialmedia_app.repository.UserRepository;
import com.groupJ.socialmedia_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(PostDTO postDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User Not Found"));

        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(user);

        postRepository.save(post);
    }

    @Override
    public List<Post> getPostByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("User Not Found"));

        return postRepository.findByAuthor(user);
    }

    @Override
    public List<Post> getPostsByUsers(List<User> users) {
        return postRepository.findByAuthorIn(users);
    }
}

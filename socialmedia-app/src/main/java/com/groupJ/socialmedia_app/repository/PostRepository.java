package com.groupJ.socialmedia_app.repository;

import com.groupJ.socialmedia_app.entity.Post;
import com.groupJ.socialmedia_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
}

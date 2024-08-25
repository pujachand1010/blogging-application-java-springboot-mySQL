package com.example.blog.service;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;


public interface PostService {
    Page<Post> getAllPosts(Pageable pageable);
    Page<Post> filterPostsByAuthor(User author, Pageable pageable);
    Page<Post> filterPostsByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Optional<Post> getPostById(Long id);
    Post createPost(Post post);
    Post updatePost(Long id, Post post) throws PostNotFoundException;
    void deletePost(Long id) throws PostNotFoundException;
}

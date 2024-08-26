package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostServiceImpl;
import com.example.blog.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @InjectMocks
    private PostController postController;

    @Mock
    private PostServiceImpl postService;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost_Success() {
        // Given
        String authorName = "author";
        String title = "title";
        String content = "content";

        // Create a User instance
        User user = new User();
        user.setUsername(authorName);
        user.setPassword("password"); // Set a dummy password or appropriate default

        // Create a Post instance to be returned from the service
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);

        // Create a Post instance to be used in the mock return value
        Post savedPost = new Post();
        savedPost.setTitle(title);
        savedPost.setContent(content);
        savedPost.setAuthor(user);

        // Mock the service methods
        when(userService.getUserByUsername(authorName)).thenReturn(user);
        when(postService.createPost(any(Post.class))).thenReturn(savedPost);

        // When
        ResponseEntity<Post> response = postController.createPost(authorName, title, content);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedPost, response.getBody());
    }

    @Test
    void testCreatePost_UserNotFound() {
        String authorName = "nonexistent";
        String title = "title";
        String content = "content";

        when(userService.getUserByUsername(authorName)).thenReturn(null);

        ResponseEntity<Post> response = postController.createPost(authorName, title, content);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreatePost_BadRequest() {
        ResponseEntity<Post> response = postController.createPost(null, "title", "content");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetPostById_Success() {
        Long postId = 1L;
        Post post = new Post();
        post.setPostId(postId);

        when(postService.getPostById(postId)).thenReturn(Optional.of(post));

        ResponseEntity<Post> response = postController.getPostById(postId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @Test
    void testGetPostById_NotFound() {
        Long postId = 1L;

        when(postService.getPostById(postId)).thenReturn(Optional.empty());

        ResponseEntity<Post> response = postController.getPostById(postId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllPosts_Success() {
        Pageable pageable = PageRequest.of(0, 10);
        Post post = new Post();
        Page<Post> postPage = new PageImpl<>(Collections.singletonList(post), pageable, 1);

        when(postService.getAllPosts(pageable)).thenReturn(postPage);

        ResponseEntity<Page<Post>> response = postController.getAllPosts(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postPage, response.getBody());
    }

}

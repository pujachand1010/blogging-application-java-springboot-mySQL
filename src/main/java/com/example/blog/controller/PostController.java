package com.example.blog.controller;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostServiceImpl;
import com.example.blog.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post, String authorName) {
        if (post.getTitle() == null || post.getContent() == null || authorName == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            User author = userService.getUserByUsername(authorName);
            post.setAuthor(author);
            Post savedPost = postService.createPost(post);
            return ResponseEntity.ok(savedPost);
        }catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> postOptional = postService.getPostById(id);
        return postOptional.map(post -> new ResponseEntity<>(post, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping
    public ResponseEntity<Page<Post>> getAllPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> posts = postService.getAllPosts(pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<Post>> getPostsByUsername(
            @PathVariable String username,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            User user = userService.getUserByUsername(username);
            Page<Post> posts = postService.filterPostsByAuthor(user, pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<Page<Post>> getPostsByDate(
            @RequestParam("startDate") String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            LocalDateTime startDate = LocalDateTime.parse(startDateStr);
            LocalDateTime endDate = (endDateStr != null) ? LocalDateTime.parse(endDateStr) : LocalDateTime.now();
            Page<Post> posts = postService.filterPostsByDate(startDate, endDate, pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(id, post);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





}

package com.example.blog.service;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service implementation for managing posts.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;  // Repository for accessing post data

    /** Retrieves all posts with pagination. */
    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /** Retrieves posts filtered by author with pagination.*/
    @Override
    public Page<Post> filterPostsByAuthor(User author, Pageable pageable) {
        return postRepository.findByAuthor(author, pageable);
    }

    /** Retrieves posts filtered by creation date range with pagination. */
    @Override
    public Page<Post> filterPostsByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        endDate = (endDate != null) ? endDate : LocalDateTime.now();  // Default to current time if endDate is null
        return postRepository.findByCreationDateBetween(startDate, endDate, pageable);
    }

    /** Retrieves a post by its ID. */
    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    /** Creates a new post. */
    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    /**
     * Updates an existing post.
     * throws PostNotFoundException if the post with the specified ID is not found
     */
    @Override
    public Post updatePost(Long id, Post post) throws PostNotFoundException {
        if (postRepository.existsById(post.getPostId())) {
            // Update the existing post
            post.setTitle(post.getTitle());
            post.setContent(post.getContent());
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException("No post found with id " + id);
        }
    }

    /**
     * Deletes a post by its ID.
     * throws PostNotFoundException if the post with the specified ID is not found
     */
    @Override
    public void deletePost(Long id) throws PostNotFoundException {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException("No post found with id " + id);
        }
    }
}

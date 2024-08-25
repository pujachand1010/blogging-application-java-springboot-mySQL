package com.example.blog.service;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> filterPostsByAuthor(Long authorId, Pageable pageable) {
        return postRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public Page<Post> filterPostsByDate(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        endDate = (endDate != null) ? endDate : LocalDateTime.now();
        return postRepository.findByCreationDateBetween(startDate, endDate, pageable);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post post) throws PostNotFoundException {
        if (postRepository.existsById(post.getPostId())) {
            post.setTitle(post.getTitle());
            post.setContent(post.getContent());
            return postRepository.save(post);
        } else {
            throw new PostNotFoundException("No post found with id " + id);
        }
    }

    @Override
    public void deletePost(Long id) throws PostNotFoundException {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new PostNotFoundException("No post found with id " + id);
        }
    }


}

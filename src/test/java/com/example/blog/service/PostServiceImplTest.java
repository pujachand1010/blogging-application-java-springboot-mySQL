package com.example.blog.service;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("username", "password", "bio"); // Adjust as needed
        post = new Post("Title", "Content", user);
        post.setPostId(1L); // Set a dummy ID
    }

    @Test
    public void testGetAllPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(List.of(post));
        when(postRepository.findAll(pageable)).thenReturn(posts);

        Page<Post> result = postService.getAllPosts(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(postRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFilterPostsByAuthor() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(List.of(post));
        when(postRepository.findByAuthor(user, pageable)).thenReturn(posts);

        Page<Post> result = postService.filterPostsByAuthor(user, pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(postRepository, times(1)).findByAuthor(user, pageable);
    }

    @Test
    public void testFilterPostsByDate() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = new PageImpl<>(List.of(post));
        when(postRepository.findByCreationDateBetween(startDate, endDate, pageable)).thenReturn(posts);

        Page<Post> result = postService.filterPostsByDate(startDate, endDate, pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(postRepository, times(1)).findByCreationDateBetween(startDate, endDate, pageable);
    }

    @Test
    public void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Optional<Post> result = postService.getPostById(1L);
        assertTrue(result.isPresent());
        assertEquals(post, result.get());
        verify(postRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreatePost() {
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.createPost(post);
        assertNotNull(result);
        assertEquals(post, result);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void testUpdatePost() throws PostNotFoundException {
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.save(post)).thenReturn(post);

        Post result = postService.updatePost(1L, post);
        assertNotNull(result);
        assertEquals(post, result);
        verify(postRepository, times(1)).save(post);
    }


    @Test
    public void testDeletePost() throws PostNotFoundException {
        when(postRepository.existsById(1L)).thenReturn(true);

        postService.deletePost(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

}

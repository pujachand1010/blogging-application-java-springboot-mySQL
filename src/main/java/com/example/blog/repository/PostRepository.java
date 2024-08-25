package com.example.blog.repository;

import com.example.blog.model.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepository extends  JpaRepository<Post, Long> {
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);
    Page<Post> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

}

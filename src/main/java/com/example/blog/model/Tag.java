package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a tag entity which can be linked to a post.
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String keyword;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

    // Default constructor for JPA
    public Tag() {
    }

    // Constructor with parameters
    public Tag(String keyword) {
        this.keyword = keyword;
    }

    // Getters and setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Set<Post> getPosts() {
        return new HashSet<>(posts);  // Return a copy to avoid external modifications
    }

    public void setPosts(Set<Post> posts) {
        this.posts = new HashSet<>(posts);  // Use a copy to avoid external modifications
    }
}

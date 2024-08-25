package com.example.blog.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String keyword;

    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts;

    //constructor
    public Tag(String keyword) {
        this.keyword = keyword;
    }

    //getters and setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String name) {
        this.keyword = keyword;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }



}

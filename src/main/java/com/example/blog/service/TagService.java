package com.example.blog.service;

import com.example.blog.exception.PostNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TagService {

    Tag createTag(Tag tag);
    Optional<Tag> getTagByName(String name) ;
    boolean tagExists(Long id);
    void deleteTag(Long id);
}

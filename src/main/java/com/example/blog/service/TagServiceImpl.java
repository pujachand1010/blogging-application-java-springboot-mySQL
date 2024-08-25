package com.example.blog.service;

import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag getTagByName(String name) {
        return tagRepository.findByKeyword(name);
    }

}

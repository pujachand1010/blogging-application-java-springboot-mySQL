package com.example.blog.service;

import com.example.blog.exception.TagNotFoundException;
import com.example.blog.model.Tag;
import com.example.blog.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing tags.
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;  // Repository for accessing tag data

    /** Creates a new tag. */
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /** Retrieves a tag by its keyword*/
    public Optional<Tag> getTagByName(String name) {
        return Optional.ofNullable(tagRepository.findByKeyword(name));
    }

    /** Checks if a tag exists by its ID.*/
    public boolean tagExists(Long id) {
        return tagRepository.existsById(id);
    }

    /**
     * Deletes a tag by its ID.
     * throws TagNotFoundException if the tag with the specified ID is not found
     */
    @Override
    public void deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
        } else {
            throw new TagNotFoundException("Tag with ID " + id + " not found");
        }
    }

}

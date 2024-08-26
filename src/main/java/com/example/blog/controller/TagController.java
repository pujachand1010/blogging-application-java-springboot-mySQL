package com.example.blog.controller;

import com.example.blog.model.Tag;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {                                        //create a tag
        if (tag.getKeyword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Tag createdTag = tagService.createTag(tag);
        return ResponseEntity.ok(createdTag);
    }

    @GetMapping("/name/{tagName}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String tagName) {                               //get tag object if tag exists by the name
        return tagService.getTagByName(tagName)
                .map(tag -> new ResponseEntity<>(tag, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {                                          //delete a tag
        if (!tagService.tagExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

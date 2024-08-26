package com.example.blog.controller;

import com.example.blog.model.Tag;
import com.example.blog.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTag_Success() {
        Tag tag = new Tag();
        tag.setKeyword("Java");

        when(tagService.createTag(any(Tag.class))).thenReturn(tag);

        ResponseEntity<Tag> response = tagController.createTag(tag);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tag, response.getBody());
        verify(tagService, times(1)).createTag(any(Tag.class));
    }

    @Test
    public void testCreateTag_BadRequest() {
        Tag tag = new Tag(); // keyword is null

        ResponseEntity<Tag> response = tagController.createTag(tag);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(tagService, never()).createTag(any(Tag.class));
    }

    @Test
    public void testDeleteTag_Success() {
        when(tagService.tagExists(1L)).thenReturn(true);

        ResponseEntity<Void> response = tagController.deleteTag(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagService, times(1)).deleteTag(1L);
    }

    @Test
    public void testDeleteTag_NotFound() {
        when(tagService.tagExists(1L)).thenReturn(false);

        ResponseEntity<Void> response = tagController.deleteTag(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tagService, never()).deleteTag(1L);
    }
}

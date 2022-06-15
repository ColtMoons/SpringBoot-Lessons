package com.acme.blogging.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.service.TagService;
import com.acme.blogging.resource.SaveTagResource;
import com.acme.blogging.resource.TagResource;

@RestController
@RequestMapping("/api")
public class TagsController {
    @Autowired
    private TagService tagService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/tags")
    public Page<TagResource> getAllTags(Pageable pageable){
        List<TagResource> tags = tagService.getAllTags(pageable).getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int tagCount = tags.size();
        return new PageImpl<>(tags, pageable, tagCount);
    }

    @GetMapping("/tags/{id}")
    public TagResource getTagById(@PathVariable(name = "id") Long tagId){
        return convertToResource(tagService.getTagById(tagId));
    }

    @PostMapping("/tags")
    public TagResource createTag(@Valid @RequestBody SaveTagResource resource){
        return convertToResource(tagService.createTag(convertToEntity(resource)));
    }

    @PutMapping("/tags/{id}")
    public TagResource updateTag(@PathVariable(name = "id") Long tagId, @Valid @RequestBody SaveTagResource resource){
        return convertToResource(tagService.updateTag(tagId,convertToEntity(resource)));
    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(name = "id") Long tagId){
        return tagService.deleteTag(tagId);
    }

    @GetMapping("/posts/{postId}/tags")
    public Page<TagResource> getAllTagsByPostId(@PathVariable Long postId, Pageable pageable){

        List<TagResource> tags = tagService.getAllTagsByPostId(postId, pageable).getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int tagCount = tags.size();

        return new PageImpl<>(tags, pageable, tagCount);
    }

    

    private Tag convertToEntity(SaveTagResource resource){
        return mapper.map(resource, Tag.class);
    }

    private TagResource convertToResource(Tag entity){
        return mapper.map(entity, TagResource.class);
    }
}

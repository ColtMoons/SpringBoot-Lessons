package com.acme.blogging.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;

@RestController
@RequestMapping("/api")
public class PostTagsController {
    
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;


    @PostMapping("/posts/{postId}/tags/{tagId}")
    public PostResource assingPostTag(@PathVariable Long postId, @PathVariable Long tagId){
        return convertToResource(postService.assignPostTag(postId, tagId));
    }

    @DeleteMapping("/posts/{postId}/tags/{tagId}")
    public PostResource unassingPostTag(@PathVariable Long postId, @PathVariable Long tagId){
        return convertToResource(postService.unassignPostTag(postId, tagId));
    }

    @GetMapping("/tags/{tagId}/posts")
    public Page<PostResource> getAllPostByTagId(@PathVariable Long tagId, Pageable pageable){
        Page<Post> postsPage = postService.getAllPostByTagId(tagId, pageable);
        List<PostResource> resources= postsPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<>(resources, pageable,resources.size());
    }

    private PostResource convertToResource(Post entity){
        return mapper.map(entity, PostResource.class);
    }

}

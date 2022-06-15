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

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class PostsController {

    //declaration of services to use 
    @Autowired
    private PostService postService; 
    
    @Autowired
    private ModelMapper mapper;

    // methots that use services

    @Operation(summary = "Get Posts", description = "Get All Post By Pages", tags = {"posts"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All Posts returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/posts")
    public Page<PostResource> getAllPost(Pageable pageable){
        Page<Post> postPage = postService.getAllPost(pageable);
        List<PostResource> resources = postPage.getContent()
        .stream()
        .map(this::convertToResource)
        .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @PostMapping("/posts")
    public PostResource createPost(@Valid @RequestBody SavePostResource resource){
        Post post = convertToEntity(resource);
        return convertToResource(postService.createPost(post));
    }

    @PutMapping("/posts/{postId}")
    public PostResource updatePost(@PathVariable Long postId, @Valid @RequestBody SavePostResource resource){
        Post post = convertToEntity(resource);
        return convertToResource(postService.updatePost(postId, post));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

    
    //convertion to resources or to entities 

    private Post convertToEntity(SavePostResource resource){
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity){
        return mapper.map(entity, PostResource.class);
    }
}

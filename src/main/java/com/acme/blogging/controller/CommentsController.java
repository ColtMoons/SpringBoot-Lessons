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

import com.acme.blogging.domain.model.Comment;
import com.acme.blogging.domain.service.CommentService;
import com.acme.blogging.resource.CommentResource;
import com.acme.blogging.resource.SaveCommentResource;

@RestController
@RequestMapping("/api")
public class CommentsController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper mapper;
    
    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResource> getAllCommentByPostId(@PathVariable Long postId, Pageable pageable){
        Page<Comment> commentPage = commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent().stream().map(
            this::convertToResource
        ).collect(Collectors.toList());
        
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource getCommentByIdAndPostId(@PathVariable Long postId, @PathVariable Long commentId){
        return convertToResource(commentService.getCommentByIdAndPostId(postId, commentId));
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResource createComment(@PathVariable Long postId, @Valid @RequestBody SaveCommentResource resource){
        return convertToResource(commentService.createComment(postId, convertToEntity(resource)));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource updateComment(@PathVariable Long postId, @PathVariable Long commentId, @Valid @RequestBody SaveCommentResource resource){
        return convertToResource(commentService.updateComment(postId, commentId, convertToEntity(resource)));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        return commentService.deleteComment(postId, commentId);
    }

    private Comment convertToEntity(SaveCommentResource resource){
        return mapper.map(resource, Comment.class);
    }

    private CommentResource convertToResource(Comment entity){
        return mapper.map(entity, CommentResource.class);
    }
}

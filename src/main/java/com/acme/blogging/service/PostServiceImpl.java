package com.acme.blogging.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.repository.TagRepository;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.exception.ResourceNotFoundException;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;
    
    @Override
    public Page<Post> getAllPost(Pageable pageable) {   
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long postId, Post postRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));

        return postRepository.save(
            post.setTitle(postRequest.getTitle())
            .setDescription(postRequest.getDescription())
            .setContent(postRequest.getContent()));
    }

    @Override
    public ResponseEntity<?> deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
        
        postRepository.delete(post);

        return ResponseEntity.ok().build();
    }

    @Override
    public Post assignPostTag(Long postId, Long tagId) {
        Tag tag=tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", tagId));

        return postRepository.findById(postId)
        .map(
            post -> postRepository.save(post.tagWith(tag))
        )
        .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public Post unassignPostTag(Long postId, Long tagId) {
        Tag tag=tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", tagId));

        return postRepository.findById(postId)
        .map(
            post -> postRepository.save(post.unTagWith(tag))
        )
        .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @Override
    public Page<Post> getAllPostByTagId(Long tagId, Pageable pageable) {
        return tagRepository.findById(tagId).map( tag ->{
            List<Post> posts = tag.getPosts();
            int postsCount = posts.size();
            return new PageImpl<>(posts, pageable, postsCount);
        }
        )
        .orElseThrow(() -> new ResourceNotFoundException("Tag", "ID", tagId));
        
    }

    @Override
    public Post getPostByTitle(String title) {
        return postRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Post", "Title", title));
    }
    
}

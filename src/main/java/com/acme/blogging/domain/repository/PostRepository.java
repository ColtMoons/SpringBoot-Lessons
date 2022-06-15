package com.acme.blogging.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.blogging.domain.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    public Optional<Post> findByTitle(String title);
}

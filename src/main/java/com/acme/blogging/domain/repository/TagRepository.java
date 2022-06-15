package com.acme.blogging.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.blogging.domain.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
}

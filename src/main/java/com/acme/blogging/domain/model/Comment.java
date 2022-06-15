package com.acme.blogging.domain.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comment extends AuditModel{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String text;


    //relations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    //getters setters
    public Long getId(){
        return id;
    }
    public Comment setId(Long id){
        this.id=id;
        return this;
    }
    public String getText(){
        return text;
    }
    public Comment setText(String text){
        this.text=text;
        return this;
    }

    public Post getPost(){
        return post;
    }

    public Comment setPost(Post post){
        this.post=post;
        return this;
    }
}

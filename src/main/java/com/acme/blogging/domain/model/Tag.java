package com.acme.blogging.domain.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "tags")
public class Tag extends AuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @NaturalId
    private String name;

    //relations
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "tags")
    private List<Post> posts;

    //getters and setters

    public Long getId(){
        return id;
    }

    public Tag setId(Long id){
        this.id=id;
        return this;
    }

    public String getName(){
        return name;
    }

    public Tag setName(String name){
        this.name=name;
        return this;
    }

    public List<Post> getPosts(){
        return posts;
    }

}

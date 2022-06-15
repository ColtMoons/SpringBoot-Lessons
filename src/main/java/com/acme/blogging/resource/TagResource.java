package com.acme.blogging.resource;

public class TagResource {
    private Long id;

    private String name;

    public Long getId(){
        return this.id;
    }

    public TagResource setId(Long id){
        this.id=id;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public TagResource setName(String name){
        this.name=name;
        return this;
    }
}

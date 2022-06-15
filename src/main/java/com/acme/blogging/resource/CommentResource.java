package com.acme.blogging.resource;

import com.acme.blogging.domain.model.AuditModel;

public class CommentResource extends AuditModel {
    private Long id;
    private String text;

    //getters setters

    public Long geId(){
        return this.id;
    }

    public CommentResource setId(Long id){
        this.id=id;
        return this;
    }

    public String getText(){
        return this.text;
    }

    public CommentResource setText(String text){
        this.text=text;
        return this;
    }
}

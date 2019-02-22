package com.app.blog.services;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;
import javassist.NotFoundException;

import java.util.Set;

public interface IPost {
    Set<Post> getPosts();
    PostCommand savePost(PostCommand postCommand, String id) throws NotFoundException;
    Post findById(Long l) throws NotFoundException;
    PostCommand findCommandById(Long l) throws NotFoundException;
    void deleteById(Long l);
}

package com.app.blog.services;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;

import java.util.Set;

public interface IPost {
    Set<Post> getPosts();
    PostCommand savePost(PostCommand postCommand, String id);
    Post findById(Long l);
    PostCommand findCommandById(Long l);
    void deleteById(Long l);
}

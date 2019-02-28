package com.app.blog.services;

import com.app.blog.commands.CommentCommand;
import com.app.blog.models.Comment;
import javassist.NotFoundException;

import java.util.Set;

public interface IComment {
    Set<Comment> getComments();
    CommentCommand findCommandById(Long l) throws NotFoundException;
    Comment findById(Long l) throws NotFoundException;
    CommentCommand saveComment(CommentCommand commentCommand, String postId, String userId) throws NotFoundException;
    void deleteById(Long l);
}

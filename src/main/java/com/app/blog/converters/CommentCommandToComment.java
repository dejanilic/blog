package com.app.blog.converters;

import com.app.blog.commands.CommentCommand;
import com.app.blog.models.Comment;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommentCommandToComment implements Converter<CommentCommand, Comment> {
    private PostCommandToPost postCommandToPost;
    private UserCommandToUser userCommandToUser;

    public CommentCommandToComment(PostCommandToPost postCommandToPost, UserCommandToUser userCommandToUser) {
        this.postCommandToPost = postCommandToPost;
        this.userCommandToUser = userCommandToUser;
    }

    @Synchronized
    @Nullable
    @Override
    public Comment convert(CommentCommand source) {
        if (source == null) {
            return null;
        }

        final Comment comment = new Comment();
        comment.setId(source.getId());
        comment.setContent(source.getContent());
        comment.setApproved(source.getApproved());
        comment.setDateCreated(source.getDateCreated());
        comment.setUser(userCommandToUser.convert(source.getUser()));
        comment.setPost(postCommandToPost.convert(source.getPost()));

        return comment;
    }
}

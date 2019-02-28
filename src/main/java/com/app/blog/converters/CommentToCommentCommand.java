package com.app.blog.converters;

import com.app.blog.commands.CommentCommand;
import com.app.blog.models.Comment;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentCommand implements Converter<Comment, CommentCommand> {
    private PostToPostCommand postToPostCommand;
    private UserToUserCommand userToUserCommand;

    public CommentToCommentCommand(PostToPostCommand postToPostCommand, UserToUserCommand userToUserCommand) {
        this.postToPostCommand = postToPostCommand;
        this.userToUserCommand = userToUserCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public CommentCommand convert(Comment source) {
        if (source == null) {
            return null;
        }

        final CommentCommand commentCommand = new CommentCommand();
        commentCommand.setId(source.getId());
        commentCommand.setContent(source.getContent());
        commentCommand.setApproved(source.getApproved());
        commentCommand.setDateCreated(source.getDateCreated());
        commentCommand.setUser(userToUserCommand.convert(source.getUser()));
        commentCommand.setPost(postToPostCommand.convert(source.getPost()));

        return commentCommand;
    }
}

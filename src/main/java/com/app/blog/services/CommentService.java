package com.app.blog.services;

import com.app.blog.commands.CommentCommand;
import com.app.blog.converters.CommentCommandToComment;
import com.app.blog.converters.CommentToCommentCommand;
import com.app.blog.models.Comment;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import com.app.blog.repositories.CommentRepositorium;
import com.app.blog.repositories.PostRepositorium;
import com.app.blog.repositories.UserRepositorium;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CommentService implements IComment {
    private final CommentRepositorium commentRepositorium;
    private final PostRepositorium postRepositorium;
    private final UserRepositorium userRepositorium;
    private final CommentCommandToComment commentCommandToComment;
    private final CommentToCommentCommand commentToCommentCommand;

    public CommentService(CommentRepositorium commentRepositorium, PostRepositorium postRepositorium, UserRepositorium userRepositorium, CommentCommandToComment commentCommandToComment, CommentToCommentCommand commentToCommentCommand) {
        this.commentRepositorium = commentRepositorium;
        this.postRepositorium = postRepositorium;
        this.userRepositorium = userRepositorium;
        this.commentCommandToComment = commentCommandToComment;
        this.commentToCommentCommand = commentToCommentCommand;
    }

    @Override
    public Set<Comment> getComments() {
        log.info("getting all comments");
        Set<Comment> comments = new HashSet<>();
        commentRepositorium.findAll().iterator().forEachRemaining(comments::add);
        return comments;
    }

    @Override
    public CommentCommand findCommandById(Long l) throws NotFoundException {
        return commentToCommentCommand.convert(findById(l));
    }

    @Override
    public Comment findById(Long l) throws NotFoundException {
        Optional<Comment> blogOptional = commentRepositorium.findById(l);
        if (!blogOptional.isPresent()) {
            throw new NotFoundException("Comment not found for ID value:" + l);
        }

        return blogOptional.get();
    }

    @Override
    public CommentCommand saveComment(CommentCommand commentCommand, String postId, String userId) throws NotFoundException {
        if (!isNumber(postId)) {
            throw new NumberFormatException(postId + " is not a number.");
        }

        if (!isNumber(userId)) {
            throw new NumberFormatException(userId + " is not a number.");
        }

        Post post = postRepositorium.findById(Long.valueOf(postId)).orElse(null);
        if (post == null) {
            throw new NotFoundException("Post not found for ID value: " + postId);
        }

        User user = userRepositorium.findById(Long.valueOf(userId)).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found for ID value: " + postId);
        }

        log.info("saving comment");
        Comment detachedComment = commentCommandToComment.convert(commentCommand);
        post.setCommentCount(post.getCommentCount() + 1);
        detachedComment.setUser(user);
        detachedComment.setPost(post);
        detachedComment.setCreatedBy(user.getUsername());

        DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
        Date dateCreated = new Date();
        detachedComment.setDateCreated(dateFormatDateCreated.format(dateCreated));

        commentRepositorium.save(detachedComment);
        return commentToCommentCommand.convert(detachedComment);
    }

    @Override
    public void deleteById(Long l){
        commentRepositorium.deleteById(l);
    }

    private Boolean isNumber(String value) {
        try {
            double d = Double.parseDouble(value);
        }
        catch(NumberFormatException e) {
            return false;
        }

        return true;
    }
}

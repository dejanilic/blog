package com.app.blog.services;

import com.app.blog.commands.PostCommand;
import com.app.blog.converters.PostCommandToPost;
import com.app.blog.converters.PostToPostCommand;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import com.app.blog.repositories.PostRepositorium;
import com.app.blog.repositories.UserRepositorium;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class PostService implements IPost{
    private final PostRepositorium postRepositorium;
    private final UserRepositorium userRepositorium;
    private final PostCommandToPost postCommandToPost;
    private final PostToPostCommand postToPostCommand;

    public PostService(PostRepositorium postRepositorium, UserRepositorium userRepositorium, PostCommandToPost postCommandToPost, PostToPostCommand postToPostCommand) {
        this.postRepositorium = postRepositorium;
        this.userRepositorium = userRepositorium;
        this.postCommandToPost = postCommandToPost;
        this.postToPostCommand = postToPostCommand;
    }

    @Override
    public Set<Post> getPosts() {
        log.info("getting all posts");
        Set<Post> posts = new HashSet<>();
        postRepositorium.findAll().iterator().forEachRemaining(posts::add);
        return posts;
    }

    @Transactional
    @Override
    public PostCommand savePost(PostCommand postCommand, String id) throws NotFoundException {
        log.info("saving post");
        User user = userRepositorium.findById(Long.valueOf(id)).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found for ID value: " + id);
        }

        Post detachedPost = postCommandToPost.convert(postCommand);
        detachedPost.setUser(user);
        detachedPost.setCreatedBy(user.getUsername());

        DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
        Date dateCreated = new Date();
        detachedPost.setDateCreated(dateFormatDateCreated.format(dateCreated));

        DateFormat dateFormatDateModified = new SimpleDateFormat("MM/dd/yyyy");
        Date dateModified = new Date();
        detachedPost.setDateModified(dateFormatDateModified.format(dateModified));
        detachedPost.setModifiedBy(user.getUsername());

        postRepositorium.save(detachedPost);

        return postCommand;
    }

    @Override
    public Post findById(Long l) throws NotFoundException {
        Optional<Post> postOptional = postRepositorium.findById(l);
        if (!postOptional.isPresent()) {
            throw new NotFoundException("User not found for ID value:" + l);
        }

        return postOptional.get();
    }

    @Transactional
    @Override
    public PostCommand findCommandById(Long l) throws NotFoundException {
        return postToPostCommand.convert(findById(l));
    }

    @Override
    public void deleteById(Long l) {
        postRepositorium.deleteById(l);
    }
}

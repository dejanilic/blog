package com.app.blog.services;

import com.app.blog.commands.PostCommand;
import com.app.blog.controllers.BlogController;
import com.app.blog.converters.PostCommandToPost;
import com.app.blog.converters.PostToPostCommand;
import com.app.blog.models.Blog;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import com.app.blog.repositories.BlogRepositorium;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService implements IPost{
    private final PostRepositorium postRepositorium;
    private final UserRepositorium userRepositorium;
    private final BlogRepositorium blogRepositorium;
    private final PostCommandToPost postCommandToPost;
    private final PostToPostCommand postToPostCommand;

    public PostService(PostRepositorium postRepositorium, UserRepositorium userRepositorium, BlogRepositorium blogRepositorium, PostCommandToPost postCommandToPost, PostToPostCommand postToPostCommand) {
        this.postRepositorium = postRepositorium;
        this.userRepositorium = userRepositorium;
        this.blogRepositorium = blogRepositorium;
        this.postCommandToPost = postCommandToPost;
        this.postToPostCommand = postToPostCommand;
    }

    @Override
    public Set<Post> getPosts() {
        log.info("getting all posts");
        Set<Post> posts = new HashSet<>();
        postRepositorium.findAll().iterator().forEachRemaining(posts::add);
        return posts.stream()
                .filter(post -> post.getBlog().getTitle().equals(BlogController.currentBlog.toString()))
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public PostCommand savePost(PostCommand postCommand, String id) throws NotFoundException {
        if (!isNumber(id)) {
            throw new NumberFormatException(id + " is not a number.");
        }
        log.info("saving post");
        User user = userRepositorium.findById(Long.valueOf(id)).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found for ID value: " + id);
        }

        Post detachedPost = postCommandToPost.convert(postCommand);
        Blog blog = blogRepositorium.findByTitle(BlogController.currentBlog.toString()).orElse(null);
        if (blog == null) {
            log.error("Add blogs in order to add a new post!");
            return new PostCommand();
        }

        detachedPost.setUser(user);
        detachedPost.setBlog(blog);
        //blog.getPosts().add(detachedPost);
        detachedPost.setCreatedBy(user.getUsername());

        DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
        Date dateCreated = new Date();
        detachedPost.setDateCreated(dateFormatDateCreated.format(dateCreated));

        DateFormat dateFormatDateModified = new SimpleDateFormat("MM/dd/yyyy");
        Date dateModified = new Date();
        detachedPost.setDateModified(dateFormatDateModified.format(dateModified));
        detachedPost.setModifiedBy(user.getUsername());

        postRepositorium.save(detachedPost);
        blogRepositorium.save(blog);

        return postCommand;
    }

    @Override
    public Post findById(Long l) throws NotFoundException {
        Optional<Post> postOptional = postRepositorium.findById(l);
        if (!postOptional.isPresent()) {
            throw new NotFoundException("Post not found for ID value:" + l);
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

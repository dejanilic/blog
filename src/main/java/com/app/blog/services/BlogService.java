package com.app.blog.services;

import com.app.blog.commands.BlogCommand;
import com.app.blog.converters.BlogCommandToBlog;
import com.app.blog.converters.BlogToBlogCommand;
import com.app.blog.models.Blog;
import com.app.blog.models.Comment;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import com.app.blog.repositories.BlogRepositorium;
import com.app.blog.repositories.CommentRepositorium;
import com.app.blog.repositories.PostRepositorium;
import com.app.blog.repositories.UserRepositorium;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class BlogService implements IBlog {
    private final BlogRepositorium blogRepositorium;
    private final UserRepositorium userRepositorium;
    private final PostRepositorium postRepositorium;
    private final CommentRepositorium commentRepositorium;
    private final BlogCommandToBlog blogCommandToBlog;
    private final BlogToBlogCommand blogToBlogCommand;

    public BlogService(BlogRepositorium blogRepositorium, UserRepositorium userRepositorium, PostRepositorium postRepositorium, CommentRepositorium commentRepositorium, BlogCommandToBlog blogCommandToBlog, BlogToBlogCommand blogToBlogCommand) {
        this.blogRepositorium = blogRepositorium;
        this.userRepositorium = userRepositorium;
        this.postRepositorium = postRepositorium;
        this.commentRepositorium = commentRepositorium;
        this.blogCommandToBlog = blogCommandToBlog;
        this.blogToBlogCommand = blogToBlogCommand;
    }

    @Transactional
    @Override
    public BlogCommand saveBlog(BlogCommand blogCommand, String id) throws Exception {
        if (!isNumber(id)) {
            throw new NumberFormatException(id + " is not a number.");
        }

        Optional<Blog> optionalBlog = blogRepositorium.findByTitle(blogCommand.getTitle());
        if (optionalBlog.isPresent()) {
            log.info("blog already exists, saving failed");
            return null;
        }
        User user = userRepositorium.findById(Long.valueOf(id)).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found for ID value: " + id);
        }

        log.info("saving blog");
        Blog detachedBlog = blogCommandToBlog.convert(blogCommand);

        user.getBlogs().add(detachedBlog);
        detachedBlog.getUsers().add(user);

        DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
        Date dateCreated = new Date();
        detachedBlog.setDateCreated(dateFormatDateCreated.format(dateCreated));

        blogRepositorium.save(detachedBlog);
        userRepositorium.save(user);

        return blogToBlogCommand.convert(detachedBlog);
    }

    @Override
    public Set<Blog> getBlogs() {
        log.info("getting all blogs");
        Set<Blog> blogs = new HashSet<>();
        blogRepositorium.findAll().iterator().forEachRemaining(blogs::add);
        return blogs;
    }

    @Override
    public Blog findById(Long l) throws NotFoundException {
        Optional<Blog> blogOptional = blogRepositorium.findById(l);
        if (!blogOptional.isPresent()) {
            throw new NotFoundException("Blog not found for ID value:" + l);
        }

        return blogOptional.get();
    }

    @Override
    public void deleteById(Long l){
        blogRepositorium.deleteById(l);
    }

    @Override
    public BlogCommand findCommandById(Long l) throws NotFoundException {
        return blogToBlogCommand.convert(findById(l));
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

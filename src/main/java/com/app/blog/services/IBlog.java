package com.app.blog.services;

import com.app.blog.commands.BlogCommand;
import com.app.blog.models.Blog;
import javassist.NotFoundException;

import java.util.Set;

public interface IBlog {
    BlogCommand saveBlog(BlogCommand blogCommand, String id) throws Exception;
    Set<Blog> getBlogs();
    BlogCommand findCommandById(Long valueOf) throws NotFoundException;
    Blog findById(Long l) throws NotFoundException;
    void deleteById(Long valueOf) throws NotFoundException;
}

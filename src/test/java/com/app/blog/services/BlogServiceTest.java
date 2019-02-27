package com.app.blog.services;

import com.app.blog.commands.BlogCommand;
import com.app.blog.converters.BlogCommandToBlog;
import com.app.blog.converters.BlogToBlogCommand;
import com.app.blog.models.Blog;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import com.app.blog.repositories.BlogRepositorium;
import com.app.blog.repositories.PostRepositorium;
import com.app.blog.repositories.UserRepositorium;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlogServiceTest {

    @Mock
    private BlogRepositorium blogRepositorium;

    @Mock
    private UserRepositorium userRepositorium;

    @Mock
    private PostRepositorium postRepositorium;

    @Mock
    private BlogCommandToBlog blogCommandToBlog;

    @Mock
    private BlogToBlogCommand blogToBlogCommand;

    private BlogService blogService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        blogService = new BlogService(blogRepositorium, userRepositorium, postRepositorium, blogCommandToBlog, blogToBlogCommand);
    }

    @Test
    public void getBlogs() {
        // given
        Blog blog = new Blog();
        blog.setId(1L);
        Set<Blog> blogSet = new HashSet<>();
        blogSet.add(blog);

        // when
        when(blogService.getBlogs()).thenReturn(blogSet);
        Set<Blog> blogs = blogService.getBlogs();

        // then
        assertEquals(1, blogs.size());
        verify(blogRepositorium, times(1)).findAll();
    }

    @Test
    public void findById() throws NotFoundException {
        Blog blog = new Blog();
        blog.setId(1L);
        Optional<Blog> optional = Optional.of(blog);

        when(blogRepositorium.findById(anyLong())).thenReturn(optional);
        Blog blogFound = blogService.findById(1L);

        assertEquals(Long.valueOf(1L), blogFound.getId());
        verify(blogRepositorium, times(1)).findById(any());
    }
}
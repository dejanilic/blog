package com.app.blog.services;

import com.app.blog.commands.PostCommand;
import com.app.blog.converters.PostCommandToPost;
import com.app.blog.converters.PostToPostCommand;
import com.app.blog.models.Post;
import com.app.blog.models.User;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Mock
    private PostRepositorium postRepositorium;

    @Mock
    private UserRepositorium userRepositorium;

    @Mock
    private PostCommandToPost postCommandToPost;

    @Mock
    private PostToPostCommand postToPostCommand;

    private PostService postService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        postService = new PostService(postRepositorium, userRepositorium, postCommandToPost, postToPostCommand);
    }

    @Test
    public void getPosts() {
        // given
        Post post = new Post();
        post.setId(1L);
        Set<Post> postSet = new HashSet<>();
        postSet.add(post);

        // when
        when(postService.getPosts()).thenReturn(postSet);
        Set<Post> posts = postService.getPosts();

        // then
        assertEquals(1, posts.size());
        verify(postRepositorium, times(1)).findAll();
    }

    @Test
    public void findById() throws NotFoundException {
        // given
        Post post = new Post();
        post.setId(1L);
        Optional<Post> optional = Optional.of(post);

        // when
        when(postRepositorium.findById(any())).thenReturn(optional);
        Post postFound = postService.findById(1L);

        // then
        assertEquals(Long.valueOf(1L), postFound.getId());
        verify(postRepositorium, times(1)).findById(any());
    }
}
package com.app.blog.controllers;

import com.app.blog.commands.PostCommand;
import com.app.blog.services.PostService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PostControllerTest {

    @Mock
    private PostService postService;

    private PostController postController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        postController = new PostController(postService);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    public void showPostsPage() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/post/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/post/show"));
    }

    @Test
    public void showNewPostPage() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/post/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/post/new"));
    }

    @Test
    public void savePost() throws Exception {
        // given
        PostCommand postCommand = new PostCommand();
        postCommand.setId(1L);
        postCommand.setTitle("some title");
        postCommand.setContent("some content");

        // when
        when(postService.savePost(any(), any())).thenReturn(postCommand);

        // then
        mockMvc.perform(post("/dashboard/user/1/post/show")
            .param("id", "")
            .param("title", "some title")
            .param("content", "some content")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard/user/1/post/show"));
    }

    @Test
    public void savePostValidationError() throws Exception {
        // when
        when(postService.savePost(any(), any())).thenReturn(null);

        // then
        mockMvc.perform(post("/dashboard/user/1/post/show")
                .param("id", "")
                .param("title", "some title")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/post/new"));
    }

    @Test
    public void editPost() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/post/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/post/new"));
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/post/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard/user/1/post/show"));
        verify(postService, times(1)).deleteById(any());
    }
}
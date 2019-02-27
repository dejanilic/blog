package com.app.blog.controllers;

import com.app.blog.commands.BlogCommand;
import com.app.blog.services.BlogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class BlogControllerTest {

    @Mock
    private BlogService blogService;

    private BlogController blogController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        blogController = new BlogController(blogService);
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @Test
    public void showBlogsPage() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/blog/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/blog/show"));
    }

    @Test
    public void showNewBlogPage() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/blog/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/blog/new"));
    }

    @Test
    public void saveBlog() throws Exception {
        // given
        BlogCommand blogCommand = new BlogCommand();
        blogCommand.setId(1L);
        blogCommand.setTitle("some blog");
        blogCommand.setDateCreated("02.26.2019");

        // when
        when(blogService.saveBlog(any(), any())).thenReturn(blogCommand);

        // then
        mockMvc.perform(post("/dashboard/user/1/blog/show")
                .param("id", "")
                .param("title", "some title")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard/user/1/blog/show"));
    }

    @Test
    public void saveBlogValidationError() throws Exception {
        // when
        when(blogService.saveBlog(any(), any())).thenReturn(null);

        // then
        mockMvc.perform(post("/dashboard/user/1/blog/show")
                .param("id", "")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/blog/new"));
    }

    @Test
    public void editBlog() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/blog/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("administrator/blog/new"));
    }

    @Test
    public void deleteBlog() throws Exception {
        mockMvc.perform(get("/dashboard/user/1/blog/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/dashboard/user/1/blog/show"));
        verify(blogService, times(1)).deleteById(any());
    }
}
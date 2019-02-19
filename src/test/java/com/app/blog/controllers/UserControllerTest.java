package com.app.blog.controllers;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.Position;
import com.app.blog.services.RoleService;
import com.app.blog.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userController = new UserController(userService, roleService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void showLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void showRegisterPage() throws Exception {
        mockMvc.perform(get("/reg"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void saveUser() throws Exception {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1l);
        userCommand.setUsername("admin");
        userCommand.setPassword("admin");
        userCommand.setEmail("mail@mail.com");
        userCommand.setPosition(Position.ADMINISTRATOR);

        // when
        when(userService.saveUser(any())).thenReturn(userCommand);

        // then
        mockMvc.perform(post("/registration")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("username", "some username")
            .param("password", "some password")
            .param("email", "email@email.com")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("register-success"));
    }

    @Test
    public void saveUserNull() throws Exception {
        when(userService.saveUser(any())).thenReturn(null);

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("username", "some username")
                .param("password", "some password")
                .param("email", "email@email.com")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/register"));
    }

    @Test
    public void saveUserValidationError() throws Exception {
        // when
        when(userService.saveUser(any())).thenReturn(null);

        // then
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("username", "some username")
                .param("password", "some password")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    public void login() throws Exception{
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1l);
        userCommand.setUsername("admin");
        userCommand.setPassword("admin");
        userCommand.setEmail("mail@mail.com");
        userCommand.setPosition(Position.ADMINISTRATOR);

        // when
        when(userService.validate(any())).thenReturn("Administrator");

        // then
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("username", "some username")
                .param("password", "some password")
                .param("email", "email@email.com")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("Administrator"));
    }

    @Test
    public void loginUserNull() throws Exception{
        // when
        when(userService.validate(any())).thenReturn(null);

        // then
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("username", "some username")
                .param("password", "some password")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
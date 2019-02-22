package com.app.blog.services;

import com.app.blog.converters.UserCommandToUser;
import com.app.blog.converters.UserToUserCommand;
import com.app.blog.models.User;
import com.app.blog.repositories.RoleRepositorium;
import com.app.blog.repositories.UserRepositorium;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserToUserCommand userToUserCommand;

    @Mock
    private UserCommandToUser userCommandToUser;

    @Mock
    private UserRepositorium userRepositorium;

    @Mock
    private RoleRepositorium roleRepositorium;

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepositorium, roleRepositorium, userCommandToUser, userToUserCommand);
    }

    @Test
    public void getUsers() {
        // given
        User user = new User();
        user.setId(1L);
        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        // when
        when(userService.getUsers()).thenReturn(userSet);
        Set<User> users = userService.getUsers();

        // then
        assertNotNull(users);
        assertEquals(users.size(), 1);
        verify(userRepositorium, times(1)).findAll();
    }
}
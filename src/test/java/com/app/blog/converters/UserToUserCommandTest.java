package com.app.blog.converters;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.Position;
import com.app.blog.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserToUserCommandTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String USERNAME = "admin";
    private static  final String PASSWORD = "admin";
    private static final String EMAIL = "mail@mail.com";
    private static final Position POSITION = Position.ADMINISTRATOR;

    private UserToUserCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new UserToUserCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new User()));
    }

    @Test
    public void convert() {
        // given
        User user = new User();
        user.setId(ID_VALUE);
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setPosition(POSITION);

        // when
        UserCommand userCommand = converter.convert(user);

        // then
        assertEquals(ID_VALUE, userCommand.getId());
        assertEquals(USERNAME, userCommand.getUsername());
        assertEquals(PASSWORD, userCommand.getPassword());
        assertEquals(EMAIL, userCommand.getEmail());
        assertEquals(POSITION, userCommand.getPosition());
    }
}
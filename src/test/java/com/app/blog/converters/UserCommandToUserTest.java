package com.app.blog.converters;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.Position;
import com.app.blog.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserCommandToUserTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String USERNAME = "admin";
    private static  final String PASSWORD = "admin";
    private static final String EMAIL = "mail@mail.com";
    private static final Position POSITION = Position.ADMINISTRATOR;

    private UserCommandToUser converter;

    @Before
    public void setUp() throws Exception {
        converter = new UserCommandToUser();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UserCommand()));
    }

    @Test
    public void convert() {
        // given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(ID_VALUE);
        userCommand.setUsername(USERNAME);
        userCommand.setPassword(PASSWORD);
        userCommand.setEmail(EMAIL);
        userCommand.setPosition(POSITION);

        // when
        User user = converter.convert(userCommand);

        // then
        assertEquals(ID_VALUE, user.getId());
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(POSITION, user.getPosition());
    }
}
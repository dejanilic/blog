package com.app.blog.converters;

import com.app.blog.commands.RoleCommand;
import com.app.blog.models.Position;
import com.app.blog.models.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleToRoleCommandTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final Position POSITION = Position.ADMINISTRATOR;

    private RoleToRoleCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RoleToRoleCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Role()));
    }

    @Test
    public void convert() {
        // given
        Role role = new Role();
        role.setId(ID_VALUE);
        role.setPosition(POSITION);

        // when
        RoleCommand roleCommand = converter.convert(role);

        // then
        assertEquals(roleCommand.getId(), ID_VALUE);
        assertEquals(roleCommand.getPosition(), POSITION);
    }
}
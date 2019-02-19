package com.app.blog.converters;

import com.app.blog.commands.RoleCommand;
import com.app.blog.models.Position;
import com.app.blog.models.Role;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleCommandToRoleTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final Position POSITION = Position.ADMINISTRATOR;

    private RoleCommandToRole converter;

    @Before
    public void setUp() throws Exception {
        converter = new RoleCommandToRole();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RoleCommand()));
    }

    @Test
    public void convert() {
        // given
        RoleCommand roleCommand = new RoleCommand();
        roleCommand.setId(ID_VALUE);
        roleCommand.setPosition(POSITION);

        // when
        Role role = converter.convert(roleCommand);

        //then
        assertEquals(role.getId(), ID_VALUE);
        assertEquals(role.getPosition(), POSITION);
    }
}
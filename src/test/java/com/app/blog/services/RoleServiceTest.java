package com.app.blog.services;

import com.app.blog.converters.RoleToRoleCommand;
import com.app.blog.models.Role;
import com.app.blog.repositories.RoleRepositorium;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceTest {

    @Mock
    private RoleRepositorium roleRepositorium;

    @Mock
    private RoleToRoleCommand roleToRoleCommand;

    private RoleService roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        roleService = new RoleService(roleRepositorium, roleToRoleCommand);
    }

    @Test
    public void getRoles() {
        Role role = new Role();
        role.setId(1L);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        when(roleService.getRoles()).thenReturn(roleSet);
        Set<Role> roles = roleService.getRoles();

        assertEquals(roles.size(), 1);
        verify(roleRepositorium, times(1)).findAll();
    }
}
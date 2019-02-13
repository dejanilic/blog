package com.app.blog.services;

import com.app.blog.converters.RoleToRoleCommand;
import com.app.blog.models.Role;
import com.app.blog.repositories.RoleRepositorium;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService implements IRole {
    private final RoleRepositorium roleRepositorium;
    private final RoleToRoleCommand roleToRoleCommand;

    public RoleService(RoleRepositorium roleRepositorium, RoleToRoleCommand roleToRoleCommand) {
        this.roleRepositorium = roleRepositorium;
        this.roleToRoleCommand = roleToRoleCommand;
    }

    @Override
    public Set<Role> getAllRoles() {
        Iterable<Role> source = roleRepositorium.findAll();
        Set<Role> roles = new HashSet<>();
        source.forEach(r -> roleToRoleCommand.convert(r));
        source.forEach(roles::add);
        return roles;
    }
}

package com.app.blog.bootstrap;

import com.app.blog.models.*;
import com.app.blog.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepositorium roleRepositorium;
    private final UserRepositorium userRepositorium;

    public DataLoader(RoleRepositorium roleRepositorium, UserRepositorium userRepositorium) {
        this.roleRepositorium = roleRepositorium;
        this.userRepositorium = userRepositorium;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadRoles();
        loadUsers();
    }

    private void loadRoles() {
        Role superAdmin = new Role();
        superAdmin.setPosition(Position.SUPER_ADMIN);

        Role administrator = new Role();
        administrator.setPosition(Position.ADMINISTRATOR);

        Role editor = new Role();
        editor.setPosition(Position.EDITOR);

        Role author = new Role();
        author.setPosition(Position.AUTHOR);

        roleRepositorium.save(superAdmin);
        roleRepositorium.save(administrator);
        roleRepositorium.save(editor);
        roleRepositorium.save(author);
    }

    private void loadUsers() {
        User superAdmin = new User();
        superAdmin.setUsername("admin");
        superAdmin.setPassword("admin");

        Role role = roleRepositorium.getRoleByPosition(Position.SUPER_ADMIN).orElse(null);

        superAdmin.setRole(role);

        userRepositorium.save(superAdmin);

        User editor = new User();
        editor.setUsername("editor");
        editor.setPassword("editor");

        Role role2 = roleRepositorium.getRoleByPosition(Position.EDITOR).orElse(null);

        editor.setRole(role2);

        userRepositorium.save(editor);
    }
}

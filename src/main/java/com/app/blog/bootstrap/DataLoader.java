package com.app.blog.bootstrap;

import com.app.blog.models.*;
import com.app.blog.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        superAdmin.setCreatedBy("program");

        DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
        Date dateCreated = new Date();
        superAdmin.setDateCreated(dateFormatDateCreated.format(dateCreated));

        DateFormat dateFormatDateModified = new SimpleDateFormat("MM/dd/yyyy");
        Date dateModified = new Date();
        superAdmin.setDateModified(dateFormatDateModified.format(dateModified));

        superAdmin.setEmail("mail@mail.com");
        superAdmin.setModifiedBy("program");

        Role role = roleRepositorium.getRoleByPosition(Position.SUPER_ADMIN).orElse(null);
        superAdmin.setRole(role);

        userRepositorium.save(superAdmin);
    }
}

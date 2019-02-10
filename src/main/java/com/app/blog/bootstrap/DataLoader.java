package com.app.blog.bootstrap;

import com.app.blog.models.*;
import com.app.blog.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepositorium roleRepositorium;

    public DataLoader(RoleRepositorium roleRepositorium) {
        this.roleRepositorium = roleRepositorium;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        load();
    }

    private void load() {
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
}

package com.app.blog.converters;

import com.app.blog.commands.RoleCommand;
import com.app.blog.models.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoleCommandToRole implements Converter<RoleCommand, Role> {

    @Synchronized
    @Nullable
    @Override
    public Role convert(RoleCommand source) {
        if (source == null) {
            return null;
        }

        final Role role = new Role();
        role.setId(source.getId());
        role.setPosition(source.getPosition());

        return role;
    }
}

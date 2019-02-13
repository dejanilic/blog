package com.app.blog.converters;

import com.app.blog.commands.RoleCommand;
import com.app.blog.models.Role;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;

@Component
public class RoleToRoleCommand implements Converter<Role, RoleCommand> {

    @Synchronized
    @Null
    @Override
    public RoleCommand convert(Role source) {
        if (source == null) {
            return null;
        }

        final RoleCommand roleCommand = new RoleCommand();
        roleCommand.setId(source.getId());
        roleCommand.setPosition(source.getPosition());

        return roleCommand;
    }
}

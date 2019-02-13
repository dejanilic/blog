package com.app.blog.converters;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    private final RoleToRoleCommand roleToRoleCommand;

    public UserToUserCommand(RoleToRoleCommand roleToRoleCommand) {
        this.roleToRoleCommand = roleToRoleCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if (source == null) {
            return null;
        }

        final UserCommand user = new UserCommand();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setRole(roleToRoleCommand.convert(source.getRole()));

        return user;
    }
}

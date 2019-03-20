package com.app.blog.converters;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if (source == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();
        userCommand.setId(source.getId());
        userCommand.setUsername(source.getUsername());
        userCommand.setPassword(source.getPassword());
        userCommand.setEmail(source.getEmail());
        userCommand.setPosition(source.getPosition());
        userCommand.setDateCreated(source.getDateCreated());
        userCommand.setDateModified(source.getDateModified());
        userCommand.setCreatedBy(source.getCreatedBy());
        userCommand.setModifiedBy(source.getModifiedBy());

        return userCommand;
    }
}

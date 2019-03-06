package com.app.blog.converters;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setPosition(source.getPosition());
        user.setDateCreated(source.getDateCreated());
        user.setDateModified(source.getDateModified());
        user.setCreatedBy(source.getCreatedBy());
        user.setModifiedBy(source.getModifiedBy());
        user.setCommentCount(source.getCommentCount());

        return user;
    }
}

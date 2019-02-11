package com.app.blog.services;

import com.app.blog.commands.UserCommand;
import com.app.blog.converters.UserCommandToUser;
import com.app.blog.converters.UserToUserCommand;
import com.app.blog.models.User;
import com.app.blog.repositories.UserRepositorium;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUser {
    private final UserRepositorium userRepositorium;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserService(UserRepositorium userRepositorium, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepositorium = userRepositorium;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public Set<User> getUsers() {
        Set<User> users = new HashSet<>();
        userRepositorium.findAll().iterator().forEachRemaining(users::add);

        return users;
    }

    @Override
    public Boolean exists(User user) {
        if (!findByUsernameAndPassword(user).isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public String validate(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        if (exists(detachedUser)) {

        }

        return null;
    }

    private Optional<User> findByUsernameAndPassword(User user) {
        return userRepositorium.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}

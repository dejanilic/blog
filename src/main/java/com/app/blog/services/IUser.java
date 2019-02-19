package com.app.blog.services;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.User;

import java.util.Set;

public interface IUser {
    Set<User> getUsers();
    Boolean exists(User user);
    String validate(UserCommand userCommand);
    UserCommand saveUser(UserCommand userCommand);
}

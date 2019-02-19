package com.app.blog.services;

import com.app.blog.commands.UserCommand;
import com.app.blog.converters.UserCommandToUser;
import com.app.blog.converters.UserToUserCommand;
import com.app.blog.models.User;
import com.app.blog.repositories.RoleRepositorium;
import com.app.blog.repositories.UserRepositorium;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService implements IUser {
    private final UserRepositorium userRepositorium;
    private final RoleRepositorium roleRepositorium;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserService(UserRepositorium userRepositorium, RoleRepositorium roleRepositorium, UserCommandToUser userCommandToUser, UserToUserCommand userToUserCommand) {
        this.userRepositorium = userRepositorium;
        this.roleRepositorium = roleRepositorium;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    public Set<User> getUsers() {
        log.info("getting all users");
        Set<User> users = new HashSet<>();
        userRepositorium.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public Boolean exists(User user) {
        if (userRepositorium.findByUsername(user.getUsername()).isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public String validate(UserCommand userCommand) {
        log.info("validating user");
        User detachedUser = userCommandToUser.convert(userCommand);
        if (exists(detachedUser)) {
            Optional<User> user = userRepositorium.findByUsernameAndPassword(userCommand.getUsername(), userCommand.getPassword());
            switch(user.get().getRole().getPosition()) {
                case SUPER_ADMIN:
                    return "Super Admin";
                case ADMINISTRATOR:
                    return "Administrator";
                case AUTHOR:
                    return "Author";
                case EDITOR:
                    return "Editor";
            }
        } else {
            return "nonexistinguser";
        }

        return "";
    }

    @Override
    @Transactional
    public UserCommand saveUser(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        if (!exists(detachedUser)) {
            log.info("saving user");
            detachedUser.setRole(roleRepositorium.getRoleByPosition(detachedUser.getPosition()).orElse(null));
            detachedUser.setCreatedBy("program");

            DateFormat dateFormatDateCreated = new SimpleDateFormat("MM/dd/yyyy");
            Date dateCreated = new Date();
            detachedUser.setDateCreated(dateFormatDateCreated.format(dateCreated));

            DateFormat dateFormatDateModified = new SimpleDateFormat("MM/dd/yyyy");
            Date dateModified = new Date();
            detachedUser.setDateModified(dateFormatDateModified.format(dateModified));
            detachedUser.setModifiedBy("program");

            userRepositorium.save(detachedUser);
            return userToUserCommand.convert(detachedUser);
        }
        return null;
    }
}

package com.app.blog.repositories;

import com.app.blog.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepositorium extends CrudRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}

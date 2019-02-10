package com.app.blog.repositories;

import com.app.blog.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositorium extends CrudRepository<User, Long> {
}

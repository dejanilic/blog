package com.app.blog.repositories;

import com.app.blog.models.Position;
import com.app.blog.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepositorium extends CrudRepository<Role, Long> {
    Optional<Role> getRoleByPosition(Position position);
}

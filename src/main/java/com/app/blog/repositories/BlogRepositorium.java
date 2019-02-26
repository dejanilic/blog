package com.app.blog.repositories;

import com.app.blog.models.Blog;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlogRepositorium extends CrudRepository<Blog, Long> {
    Optional<Blog> findByTitle(String title);
}

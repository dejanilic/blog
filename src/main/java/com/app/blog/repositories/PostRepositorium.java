package com.app.blog.repositories;

import com.app.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepositorium extends CrudRepository<Post, Long> {
    Optional<Post> findByBlogId(Long id);
    Iterable<Post> findAllByBlogId(Long id);
}

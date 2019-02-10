package com.app.blog.repositories;

import com.app.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepositorium extends CrudRepository<Post, Long> {
}

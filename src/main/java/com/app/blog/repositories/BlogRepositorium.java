package com.app.blog.repositories;

import com.app.blog.models.Blog;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepositorium extends CrudRepository<Blog, Long> {
}

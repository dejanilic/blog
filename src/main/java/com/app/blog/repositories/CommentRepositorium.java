package com.app.blog.repositories;

import com.app.blog.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepositorium extends CrudRepository<Comment, Long> {
}

package com.app.blog.repositories;

import com.app.blog.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepositorium extends CrudRepository<Post, Long> {
    Optional<Post> findByBlogId(Long id);
    Iterable<Post> findAllByBlogId(Long id);

    /*@Query("select s from Post s where s.title like ?1 and s.content like 'post1' ")
    Iterable<Post> findByTitle(String title);*/

    Iterable<Post> findFirst3ByContentNotLikeOrderByIdDesc(String a);
}

package com.app.blog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Lob
    private String content;
    private String dateCreated;
    private String createdBy;
    private String dateModified;
    private String modifiedBy;

    // user id

    // blog id
    @ManyToMany
    @JoinTable(name = "post_blog", joinColumns = @JoinColumn(name = "post_id"),
    inverseJoinColumns = @JoinColumn(name = "blog_id"))
    private Set<Blog> blogs = new HashSet<Blog>();
}

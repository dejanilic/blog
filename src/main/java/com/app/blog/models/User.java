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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String dateCreated;
    private String createdBy;
    private String dateModified;
    private String modifiedBy;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new HashSet<Post>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

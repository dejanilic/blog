package com.app.blog.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;
import org.springframework.context.annotation.Primary;

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

    @Transient
    private Position position;

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<Blog> blogs = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new HashSet<Post>();

    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new HashSet<Comment>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}

package com.app.blog.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {
    private Long id;
    private String username;
    private String password;
    private String email;
    private RoleCommand role;
}

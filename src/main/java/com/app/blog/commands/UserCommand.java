package com.app.blog.commands;

import com.app.blog.models.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20)
    private String username;

    @NotBlank
    @Size(min = 1)
    private String password;

    @NotBlank
    @Email
    private String email;
    private Position position;
}

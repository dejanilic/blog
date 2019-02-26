package com.app.blog.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BlogCommand {
    private Long id;

    @NotBlank
    private String title;
    private String dateCreated;
}

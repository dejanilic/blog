package com.app.blog.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CommentCommand {
    private Long id;

    @Size(min = 1, max = 255)
    private String content;
    private Boolean approved = false;
    private String dateCreated;
    private PostCommand post;
    private UserCommand user;
}

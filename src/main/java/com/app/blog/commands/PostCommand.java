package com.app.blog.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class PostCommand {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private Integer commentCount;
}

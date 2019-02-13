package com.app.blog.commands;

import com.app.blog.models.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleCommand {
    private Long id;
    private Position position;
}

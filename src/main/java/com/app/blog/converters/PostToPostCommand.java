package com.app.blog.converters;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PostToPostCommand implements Converter<Post, PostCommand> {

    @Synchronized
    @Nullable
    @Override
    public PostCommand convert(Post source) {
        if (source == null) {
            return null;
        }

        final PostCommand postCommand = new PostCommand();
        postCommand.setId(source.getId());
        postCommand.setTitle(source.getTitle());
        postCommand.setContent(source.getContent());

        return postCommand;
    }
}

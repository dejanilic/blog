package com.app.blog.converters;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class PostCommandToPost implements Converter<PostCommand, Post> {

    @Synchronized
    @Nullable
    @Override
    public Post convert(PostCommand source) {
        if (source == null) {
            return null;
        }

        final Post post = new Post();
        post.setId(source.getId());
        post.setTitle(source.getTitle());
        post.setContent(source.getContent());
        post.setCommentCount(source.getCommentCount());

        return post;
    }
}

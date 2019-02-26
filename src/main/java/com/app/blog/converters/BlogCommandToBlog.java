package com.app.blog.converters;

import com.app.blog.commands.BlogCommand;
import com.app.blog.models.Blog;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BlogCommandToBlog implements Converter<BlogCommand, Blog> {

    @Synchronized
    @Nullable
    @Override
    public Blog convert(BlogCommand source) {
        if (source == null) {
            return null;
        }

        final Blog blog = new Blog();
        blog.setId(source.getId());
        blog.setTitle(source.getTitle());
        blog.setDateCreated(source.getDateCreated());

        return blog;
    }
}

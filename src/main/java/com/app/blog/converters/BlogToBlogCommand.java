package com.app.blog.converters;

import com.app.blog.commands.BlogCommand;
import com.app.blog.models.Blog;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class BlogToBlogCommand implements Converter<Blog, BlogCommand> {

    @Synchronized
    @Nullable
    @Override
    public BlogCommand convert(Blog source) {
        if (source == null) {
            return null;
        }

        final BlogCommand blogCommand = new BlogCommand();
        blogCommand.setId(source.getId());
        blogCommand.setTitle(source.getTitle());
        blogCommand.setDateCreated(source.getDateCreated());

        return blogCommand;
    }
}

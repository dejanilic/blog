package com.app.blog.converters;

import com.app.blog.commands.BlogCommand;
import com.app.blog.commands.PostCommand;
import com.app.blog.models.Blog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlogCommandToBlogTest {
    private static final Long ID_VALUE = 1L;
    private static final String TITLE = "some title";

    private BlogCommandToBlog converter;

    @Before
    public void setUp() throws Exception {
        converter = new BlogCommandToBlog();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new BlogCommand()));
    }

    @Test
    public void convert() {
        // given
        BlogCommand blogCommand = new BlogCommand();
        blogCommand.setId(ID_VALUE);
        blogCommand.setTitle(TITLE);

        // when
        Blog blog = converter.convert(blogCommand);

        // then
        assertEquals(ID_VALUE, blog.getId());
        assertEquals(TITLE, blog.getTitle());
    }
}
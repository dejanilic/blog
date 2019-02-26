package com.app.blog.converters;

import com.app.blog.commands.BlogCommand;
import com.app.blog.models.Blog;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlogToBlogCommandTest {
    private static final Long ID_VALUE = 1L;
    private static final String TITLE = "some title";

    private BlogToBlogCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new BlogToBlogCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Blog()));
    }

    @Test
    public void convert() {
        // given
        Blog blog = new Blog();
        blog.setId(ID_VALUE);
        blog.setTitle(TITLE);

        // when
        BlogCommand blogCommand = converter.convert(blog);

        // then
        assertEquals(ID_VALUE, blogCommand.getId());
        assertEquals(TITLE, blogCommand.getTitle());
    }
}
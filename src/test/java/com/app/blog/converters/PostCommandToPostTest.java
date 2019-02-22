package com.app.blog.converters;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostCommandToPostTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String TITLE = "some title";
    private static  final String CONTENT = "some content";

    private PostCommandToPost converter;

    @Before
    public void setUp() throws Exception {
        converter = new PostCommandToPost();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new PostCommand()));
    }

    @Test
    public void convert() {
        // given
        PostCommand postCommand = new PostCommand();
        postCommand.setId(ID_VALUE);
        postCommand.setTitle(TITLE);
        postCommand.setContent(CONTENT);

        // when
        Post post = converter.convert(postCommand);

        // then
        assertEquals(ID_VALUE, post.getId());
        assertEquals(TITLE, post.getTitle());
        assertEquals(CONTENT, post.getContent());
    }
}
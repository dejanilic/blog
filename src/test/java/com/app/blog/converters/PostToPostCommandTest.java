package com.app.blog.converters;

import com.app.blog.commands.PostCommand;
import com.app.blog.models.Post;
import com.app.blog.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostToPostCommandTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String TITLE = "some title";
    private static  final String CONTENT = "some content";

    private PostToPostCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new PostToPostCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Post()));
    }

    @Test
    public void convert() {
        // given
        Post post = new Post();
        post.setId(ID_VALUE);
        post.setTitle(TITLE);
        post.setContent(CONTENT);

        // when
        PostCommand postCommand = converter.convert(post);

        // then
        assertEquals(ID_VALUE, postCommand.getId());
        assertEquals(TITLE, postCommand.getTitle());
        assertEquals(CONTENT, postCommand.getContent());
    }
}
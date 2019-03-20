package com.app.blog.controllers;

import com.app.blog.commands.CommentCommand;
import com.app.blog.models.Comment;
import com.app.blog.models.Post;
import com.app.blog.repositories.CommentRepositorium;
import com.app.blog.repositories.PostRepositorium;
import com.app.blog.services.BlogService;
import com.app.blog.services.PostService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
public class SiteController {
    private final PostService postService;
    private final PostRepositorium postRepositorium;
    private final BlogService blogService;
    private final CommentRepositorium commentRepositorium;

    public SiteController(PostService postService, PostRepositorium postRepositorium, BlogService blogService, CommentRepositorium commentRepositorium) {
        this.postService = postService;
        this.postRepositorium = postRepositorium;
        this.blogService = blogService;
        this.commentRepositorium = commentRepositorium;
    }

    @RequestMapping(value = "/dashboard/user/{id}/site", method = RequestMethod.GET)
    public String showSite(Model model, @PathVariable String id) {
        Iterable<Post> posts = postRepositorium.findFirst3ByContentNotLikeOrderByIdDesc("");
        Iterable<Comment> comments = commentRepositorium.findFirst3ByContentNotLikeOrderByIdDesc("");
        List<Post> sortedList = new ArrayList<>(postService.getPosts());
        Collections.sort(sortedList, Comparator.comparing(Post::getId));
        model.addAttribute("userid", id);
        model.addAttribute("posts", sortedList);
        model.addAttribute("latestposts", posts);
        model.addAttribute("latestscomments", comments);
        model.addAttribute("currentblog", BlogController.currentBlog);
        return "site";
    }

    @RequestMapping(value = "/dashboard/user/{userid}/site/post/{postid}", method = RequestMethod.GET)
    public String showSinglePost(@PathVariable String userid, @PathVariable String postid, Model model) throws NotFoundException {
        Post post = postService.findById(Long.valueOf(postid));
        Iterable<Comment> comments = commentRepositorium.findAllByPostId(Long.valueOf(post.getId()));
        model.addAttribute("userid", userid);
        model.addAttribute("postid", postid);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new CommentCommand());
        return "post";
    }

    @RequestMapping(value = "/site", method = RequestMethod.GET)
    public String showLandingPage(Model model) {
        model.addAttribute("blogs", blogService.getBlogs());
        return "landing";
    }

    @RequestMapping(value = "/site", method = RequestMethod.POST)
    public String showSiteFromLandingPage(RedirectAttributes redirectAttributes, @RequestParam String blog) {
        log.info("showing site page...");
        if (blog.equals("")) {
            redirectAttributes.addFlashAttribute("isselected", false);
            return "redirect:/site";
        }

        return "unregisted-site";
    }
}

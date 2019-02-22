package com.app.blog.controllers;

import com.app.blog.commands.PostCommand;
import com.app.blog.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
public class PostController {
    private final PostService postService;

    private static Boolean isSaved = false;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/dashboard/user/{id}/post/show", method = RequestMethod.GET)
    public String showPostsPage(@PathVariable String id, Model model) {
        log.info("showing posts page");
        model.addAttribute("posts", postService.getPosts());
        model.addAttribute("id", id);
        if (isSaved.equals(true)) {
            model.addAttribute("issaved", true);
            isSaved = false;
        }

        return "administrator/post/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/post/new", method = RequestMethod.GET)
    public String showNewPostPage(@PathVariable String id, Model model) {
        log.info("showing new post page");
        model.addAttribute("post", new PostCommand());
        model.addAttribute("id", id);
        return "administrator/post/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/post/show", method = RequestMethod.POST)
    public String savePost(@PathVariable String id, @Valid @ModelAttribute("post") PostCommand postCommand, BindingResult result) {
        log.info("showing posts page");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "administrator/post/new";
        }

        PostCommand savedPost = postService.savePost(postCommand, id);
        isSaved = true;
        return "redirect:/dashboard/user/" + id + "/post/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/post/{postid}/edit", method = RequestMethod.GET)
    public String editPost(@PathVariable String id, @PathVariable String postid, Model model) {
        log.info("editing post");
        model.addAttribute("post", postService.findCommandById(Long.valueOf(postid)));
        return "administrator/post/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/post/{postid}/delete", method = RequestMethod.GET)
    public String deletePost(@PathVariable String id, @PathVariable String postid) {
        log.info("deleting post");
        postService.deleteById(Long.valueOf(postid));
        return "redirect:/dashboard/user/" + id + "/post/show";
    }
}

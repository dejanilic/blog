package com.app.blog.controllers;

import com.app.blog.commands.BlogCommand;
import com.app.blog.services.BlogService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
public class BlogController {
    private final BlogService blogService;

    private static Boolean isSaved = false;
    public static StringBuilder currentBlog = new StringBuilder("blog 1");

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(value = "/dashboard/user/{id}/blog/show", method = RequestMethod.GET)
    public String showBlogsPage(@PathVariable String id, Model model) {
        log.info("showing blogs page");
        model.addAttribute("blogs", blogService.getBlogs());
        model.addAttribute("id", id);
        model.addAttribute("currentblog", currentBlog.toString());
        if (isSaved.equals(true)) {
            model.addAttribute("issaved", true);
            isSaved = false;
        }

        //exists = false;
        return "administrator/blog/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/blog/new", method = RequestMethod.GET)
    public String showNewBlogPage(@PathVariable String id, Model model) {
        log.info("showing new blog page");
        model.addAttribute("blog", new BlogCommand());
        model.addAttribute("id", id);
        return "administrator/blog/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/blog/show", method = RequestMethod.POST)
    public String saveBlog(@PathVariable String id, @Valid @ModelAttribute("blog") BlogCommand blogCommand, BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
        log.info("showing blogs page from savePost method");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "administrator/blog/new";
        }

        BlogCommand savedBlog = blogService.saveBlog(blogCommand, id);
        if (savedBlog == null) {
            redirectAttributes.addFlashAttribute("exist", true);
            return "redirect:/dashboard/user/" + id + "/blog/show";
        }

        isSaved = true;
        return "redirect:/dashboard/user/" + id + "/blog/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/blog/{blogid}/edit", method = RequestMethod.GET)
    public String editBlog(@PathVariable String id, @PathVariable String blogid, Model model) throws NotFoundException {
        log.info("editing blog");
        currentBlog.setLength(0);
        model.addAttribute("blog", blogService.findCommandById(Long.valueOf(blogid)));
        return "administrator/blog/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/blog/{blogid}/delete", method = RequestMethod.GET)
    public String deleteBlog(@PathVariable String id, @PathVariable String blogid, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("deleting blog");
        blogService.deleteById(Long.valueOf(blogid));
        redirectAttributes.addFlashAttribute("deleted", true);
        BlogController.currentBlog.setLength(0);
        return "redirect:/dashboard/user/" + id + "/blog/show";
    }

    @RequestMapping(value="/dashboard/user/{id}/blog/changeblog/{value}", method=RequestMethod.GET)
    public String changeselectedBlog(@PathVariable String id, @PathVariable String value, RedirectAttributes redirectAttributes) {
        currentBlog.setLength(0);
        currentBlog.append(value);
        redirectAttributes.addFlashAttribute("changed", true);
        return "redirect:/dashboard/user/" + id + "/blog/show";
    }
}

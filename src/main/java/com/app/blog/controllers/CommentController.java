package com.app.blog.controllers;

import com.app.blog.commands.CommentCommand;
import com.app.blog.services.CommentService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/dashboard/user/{id}/comment/show", method = RequestMethod.GET)
    public String showCommentPage(@PathVariable String id, Model model) {
        log.info("showing comments page");
        model.addAttribute("id", id);
        model.addAttribute("comments", commentService.getComments());
        return "administrator/comment/show";
    }

    @RequestMapping(value = "/dashboard/user/{userid}/post/{postid}/comment/{commentid}/edit", method = RequestMethod.GET)
    public String showEditCommentPage(@PathVariable String userid, @PathVariable String postid, @PathVariable String commentid, Model model) throws NotFoundException {
        log.info("showing edit comment page");
        model.addAttribute("userid", userid);
        model.addAttribute("postid", postid);
        model.addAttribute("commentid", commentid);
        model.addAttribute("comment", commentService.findCommandById(Long.valueOf(commentid)));
        return "administrator/comment/new";
    }

    @RequestMapping(value = "/dashboard/user/{userid}/post/{postid}/comment/{commentid}/show", method = RequestMethod.POST)
    public String saveComment(@Valid @ModelAttribute("comment") CommentCommand commentCommand, BindingResult result, @PathVariable String userid, @PathVariable String postid, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("showing comment page from saveComment method");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "administrator/comment/new";
        }

        redirectAttributes.addFlashAttribute("saved", true);
        CommentCommand savedComment = commentService.saveComment(commentCommand, postid, userid);
        return "redirect:/dashboard/user/" + userid + "/comment/show";
    }

    @RequestMapping(value = "/dashboard/user/{userid}/site/post/{postid}/save", method = RequestMethod.POST)
    public String saveCommentFromSite(@Valid @ModelAttribute("comment") CommentCommand commentCommand, BindingResult result, @PathVariable String userid, @PathVariable String postid, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("saving comments from saveCommentFromSite method");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            redirectAttributes.addFlashAttribute("saved", false);
            return "redirect:/dashboard/user/" + userid + "/site/post/" + postid;
        }

        CommentCommand savedComment = commentService.saveComment(commentCommand, postid, userid);
        redirectAttributes.addFlashAttribute("saved", true);
        return "redirect:/dashboard/user/" + userid + "/site/post/" + postid;
    }

    @RequestMapping(value = "/dashboard/user/{userid}/post/{postid}/comment/{commentid}/delete", method = RequestMethod.GET)
    public String deleteComment(@PathVariable String commentid, @PathVariable String userid, RedirectAttributes redirectAttributes){
        commentService.deleteById(Long.valueOf(commentid));
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/dashboard/user/" + userid + "/comment/show";
    }
}

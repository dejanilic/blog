package com.app.blog.controllers;

import com.app.blog.commands.UserCommand;
import com.app.blog.services.RoleService;
import com.app.blog.services.UserService;
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
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private static Boolean isSaved = false;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        log.info("showing login page");
        model.addAttribute("user", new UserCommand());
        if (isSaved.equals(true)) {
            model.addAttribute("issaved", true);
            isSaved = false;
        }

        return "login";
    }

    @RequestMapping(value = {"/register", "/reg"}, method = RequestMethod.GET)
    public String showRegisterPage(Model model) {
        log.info("showing register page");
        model.addAttribute("user", new UserCommand());
        model.addAttribute("role", roleService.getRoles());
        return "register";
    }

    @RequestMapping(value = "/dashboard/user/{id}", method = RequestMethod.GET)
    public String showDashboardPage(@PathVariable String id, Model model) {
        log.info("showing dashboard page");
        model.addAttribute("id", id);
        return "administrator/dashboard";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") UserCommand command, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.info(objectError.toString());
            });
            return "register";
        }

        log.info("calling saveUser method");
        UserCommand userCommand = userService.saveUser(command);
        if(userCommand == null) {
            redirectAttributes.addFlashAttribute("issaved", false);
            return "redirect:/register";
        }

        isSaved = true;
        return "redirect:/login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute("user") UserCommand command, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.info(objectError.toString());
            });
            return "login";
        }

        log.info("logging user");
        String menu = userService.validate(command);

        if (menu.equals("nonexistinguser")) {
            redirectAttributes.addFlashAttribute("menu", "nonexistinguser");
            return "redirect:/login";
        }

        return menu;
    }
}

package com.app.blog.controllers;

import com.app.blog.commands.UserCommand;
import com.app.blog.services.RoleService;
import com.app.blog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        log.info("showing login page");
        model.addAttribute("user", new UserCommand());
        return "login";
    }

    @RequestMapping(value = {"/register", "/reg"}, method = RequestMethod.GET)
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserCommand());
        model.addAttribute("role", roleService.getAllRoles());
        return "register";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("login") UserCommand command, BindingResult result) {
        // check binding result
        userService.saveUser(command);
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
        System.out.println(menu.toUpperCase());
    }
}

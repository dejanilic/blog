package com.app.blog.controllers;

import com.app.blog.commands.UserCommand;
import com.app.blog.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        model.addAttribute("user", new UserCommand());
        return "login";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public void login(@Valid @ModelAttribute("user") UserCommand command, BindingResult result) {
        // provera za formu

        // logika za prikaz stranica
        String menu = userService.validate(command);
        System.out.println(menu.toUpperCase());
    }
}

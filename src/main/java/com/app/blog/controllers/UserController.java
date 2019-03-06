package com.app.blog.controllers;

import com.app.blog.commands.UserCommand;
import com.app.blog.models.User;
import com.app.blog.services.RoleService;
import com.app.blog.services.UserService;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    public String saveRegistedUser(@Valid @ModelAttribute("user") UserCommand command, BindingResult result, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("showing user page from saveRegistedUser method");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.info(objectError.toString());
            });
            return "register";
        }

        UserCommand userCommand = userService.saveUser(command, "");
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

    @RequestMapping(value = "/dashboard/user/{id}/user/show", method = RequestMethod.GET)
    public String showUsersPage(@PathVariable String id, Model model) throws NotFoundException {
        log.info("showing users page");
        User user = userService.findById(Long.valueOf(id));
        model.addAttribute("users", userService.getUsers().stream().filter(u -> u.getCreatedBy().equals(user.getUsername())).collect(Collectors.toSet()));
        model.addAttribute("id", id);
        return "administrator/user/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/user/new", method = RequestMethod.GET)
    public String showNewUserPage(@PathVariable String id, Model model) {
        log.info("showing new user page");
        model.addAttribute("user", new UserCommand());
        model.addAttribute("id", id);
        return "administrator/user/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/user/show", method = RequestMethod.POST)
    public String saveUser(@PathVariable String id, @Valid @ModelAttribute("user") UserCommand userCommand, BindingResult result, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("showing users page from saveUser method");
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return "administrator/user/new";
        }

        UserCommand savedUser = userService.saveUser(userCommand, id);
        System.out.println("OK");
        if (savedUser == null) {
            redirectAttributes.addFlashAttribute("exist", true);
        } else {
            redirectAttributes.addFlashAttribute("exist", false);
        }

        return "redirect:/dashboard/user/" + id + "/user/show";
    }

    @RequestMapping(value = "/dashboard/user/{id}/user/{usertoeditid}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable String id, @PathVariable String usertoeditid, Model model) throws NotFoundException {
        log.info("editing user");
        model.addAttribute("user", userService.findCommandById(Long.valueOf(usertoeditid)));
        return "administrator/user/new";
    }

    @RequestMapping(value = "/dashboard/user/{id}/user/{useridtodelete}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable String id, @PathVariable String useridtodelete, RedirectAttributes redirectAttributes) throws NotFoundException {
        log.info("deleting user");
        userService.deleteById(Long.valueOf(useridtodelete));
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/dashboard/user/" + id + "/user/show";
    }
}

package com.gmail.tinstefanic.minesweeperweb.controllers;

import com.gmail.tinstefanic.minesweeperweb.dtos.UserDto;
import com.gmail.tinstefanic.minesweeperweb.exceptions.UserAlreadyExistsException;
import com.gmail.tinstefanic.minesweeperweb.services.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MainController {

    private final RegisterUserService registerUserService;

    @Autowired
    public MainController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:menu";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null) return "redirect:menu";
        else return "login";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/register")
    public String register(Authentication authentication, Model model) {
        if (authentication != null) return "redirect:menu";
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors()) return "register";

        try {
            this.registerUserService.registerNewUser(userDto);
        } catch (UserAlreadyExistsException uaeEx) {
            model.addAttribute("usernameTaken", "An user with that username already exists.");
            return "register";
        }

        return"redirect:menu";
    }
}

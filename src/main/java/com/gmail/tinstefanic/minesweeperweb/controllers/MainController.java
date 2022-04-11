package com.gmail.tinstefanic.minesweeperweb.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

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
}

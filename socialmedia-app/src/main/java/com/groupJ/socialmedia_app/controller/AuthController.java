package com.groupJ.socialmedia_app.controller;

import com.groupJ.socialmedia_app.dto.RegisterDTO;
import com.groupJ.socialmedia_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/process-register")
    public String registerUser(@Valid @ModelAttribute RegisterDTO registerDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(registerDTO);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}

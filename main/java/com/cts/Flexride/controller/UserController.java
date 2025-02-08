package com.cts.Flexride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.cts.Flexride.Entity.UserEntity;
import com.cts.Flexride.Service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Show Login Page
    @GetMapping("/login")
    public String showLoginPage(HttpServletResponse response, Model model) {
    	
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        return "login";
    }

    // Show Signup Page
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "signup";
    }

    // Handle Signup Form Submission
    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserEntity user, Model model) {
        userService.saveUser(user);
        model.addAttribute("message", "Signup successful! Please login.");
        return "login";
    }

    // Handle Login
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model, HttpServletResponse response) {
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        if (userService.validateUser(email, password)) {
        	
       int userId = userService.getUserId(email, password);
       
            session.setAttribute("userEmail", email);
            session.setAttribute("userId", userId);
            return "redirect:/user/customerdashboard"; // Redirect to customer dashboard
        } else {
            model.addAttribute("error", "Invalid email or password. Please try again.");
            return "login";
        }
    }

    // Show Customer Dashboard
    @GetMapping("/customerdashboard")
    public String showDashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) {
            return "redirect:/user/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("email", email);
        return "customerdashboard";
    }

    // Logout
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}

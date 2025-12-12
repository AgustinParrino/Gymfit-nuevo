package com.gymfit.controller;

import com.gymfit.model.User;
import com.gymfit.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String loginForm() { return "login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, Model model, HttpSession session) {
        var user = userRepo.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/register")
    public String registerForm() { return "register"; }

    @PostMapping("/register")
    public String doRegister(@RequestParam String name, @RequestParam String email, @RequestParam String password, Model model) {
        if (name==null || name.isBlank() || email==null || email.isBlank() || password==null || password.isBlank()) {
            model.addAttribute("error","Todos los campos son obligatorios");
            return "register";
        }
        var exists = userRepo.findByEmail(email);
        if (exists.isPresent()) {
            model.addAttribute("error","Email ya registrado");
            return "register";
        }
        User u = new User(null, name, email, password);
        userRepo.save(u);
        model.addAttribute("msg","Registro exitoso. Iniciá sesión.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}

package com.lnr.authentication_service.auth.infrastructure.primary.controller;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RegistrationPageController {

    private final AccountApplicationService accountService;

    private final PasswordEncoder passwordEncoder;


    // Show registration form
    @GetMapping("/register")
    public String showForm(Model model) {

        model.addAttribute("register", new RestAccount("",""));

        return "register"; // points to src/main/resources/templates/register.html
    }


    // Handle form submission
    @PostMapping("/register")
    public String handleForm(@ModelAttribute RestAccount register, Model model) {

        try{
            accountService.RegisterAccount(
                    RestAccount.toDomain(register, passwordEncoder, new UserPublicId(UUID.randomUUID()))
            );


        model.addAttribute("success", true);

        return "login"; // points to src/main/resources/templates/login.html

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("register", register); // preserve form input
            return "register"; // back to registration page
        }
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {

        model.addAttribute("register", new RestAccount("",""));

        return "login"; // points to src/main/resources/templates/login.html
    }

    //home page
    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {

        return "home";   // points to src/main/resources/templates/home.html
    }

    //After logout user send to register
    @GetMapping("/logout")
    public String showRegisterPage(){

        return "register";
    }

}


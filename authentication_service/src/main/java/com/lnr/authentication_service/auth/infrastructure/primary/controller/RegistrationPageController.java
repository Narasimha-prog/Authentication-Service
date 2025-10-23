package com.lnr.authentication_service.auth.infrastructure.primary.controller;



import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
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
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

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
            accountService.saveAccount(
                    RestAccount.toDomain(register, passwordEncoder, new UserPublicId(UUID.randomUUID()))
            );


        model.addAttribute("success", true);
        return "login"; // redirect user to default login page

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("register", register); // preserve form input
            return "register"; // back to registration page
        }
    }

    // Show registration form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("register", new RestAccount("",""));
        return "login"; // points to src/main/resources/templates/login.html
    }
    @PostMapping("/login_sub")
   public String handleLoginForm(@ModelAttribute RestAccount login,Model model){
    try{
       UserAccount userAccount= accountService.findAccountByEmail(new UserEmail(login.email()));
       RefreshToken token= accountService.findTokenByPublicId(userAccount.getPublicId());
        if(token==null){
            model.addAttribute("error", "login with valid credentials...");
            return "login";
        }
    } catch (RuntimeException e) {
        {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
       return "sucess";
   }
}


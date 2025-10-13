package com.lnr.authentication_service.auth.infrastructure.primary;

import com.lnr.authentication_service.user.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestResource {

    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/login")
    public String login(@RequestBody RestRegister register) {
      return  null;
    }

    @GetMapping("/validate")
    public Long validateToken(@RequestParam String token) {
        return  null;
    }
}

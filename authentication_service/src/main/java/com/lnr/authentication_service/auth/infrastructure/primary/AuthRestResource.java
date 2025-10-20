package com.lnr.authentication_service.auth.infrastructure.primary;

import com.lnr.authentication_service.auth.application.AuthApplicationService;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthRestResource {

    private AuthApplicationService service;


    @PostMapping("/login")
    public String login(@RequestBody RestRegister register) {
      return  null;
    }

    @GetMapping("/validate")
    public Long validateToken(@RequestParam String token) {
        return  null;
    }


}

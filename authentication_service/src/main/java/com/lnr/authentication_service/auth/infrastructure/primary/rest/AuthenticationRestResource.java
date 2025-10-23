package com.lnr.authentication_service.auth.infrastructure.primary.rest;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAccount;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAuthTokens;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthenticationRestResource {


   private final AccountApplicationService service;

   private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<RestAuthTokens> login(@RequestBody RestAccount register) {
       RestAuthTokens tokens= RestAuthTokens.fromDomain(service.saveAccount(RestAccount.toDomain(register,encoder, new UserPublicId(UUID.randomUUID()))));
      return  ResponseEntity.status(HttpStatus.CREATED).body(tokens);
    }

    @GetMapping("/validate")
    public Long validateToken(@RequestParam String token) {
        return  null;
    }


}

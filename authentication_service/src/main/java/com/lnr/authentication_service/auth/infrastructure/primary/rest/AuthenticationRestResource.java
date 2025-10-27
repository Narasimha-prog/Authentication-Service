package com.lnr.authentication_service.auth.infrastructure.primary.rest;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAccount;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestRegisterResponse;
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
    public ResponseEntity<RestRegisterResponse> register(@RequestBody RestAccount register) {
       UserAccount newUser= service.RegisterAccount(RestAccount.toDomain(register,encoder, new UserPublicId(UUID.randomUUID())));
      return  ResponseEntity.status(HttpStatus.CREATED).body(new RestRegisterResponse(newUser.getPublicId().value(),"User registered successfully"));
    }

    @GetMapping("/test/chay")
    public String hello(){
        return "hi developer";
    }
//    public ResponseEntity<RestAuthTokens>
//
//    @GetMapping("/validate")
//    public Long validateToken(@RequestParam String token) {
//        return  null;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<RestAuthTokens> login(@RequestBody RestAccount login) {
//        RestAuthTokens tokens = RestAuthTokens.fromDomain(service.loginAccount(login));
//        return ResponseEntity.ok(tokens);
//    }

}

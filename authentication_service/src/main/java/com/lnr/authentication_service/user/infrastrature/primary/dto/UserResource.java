package com.lnr.authentication_service.user.infrastrature.primary.dto;

import com.lnr.authentication_service.user.application.UserApplicationService;
import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/api")
public class UserResource {


    private final UserApplicationService service;


    @GetMapping()
    public ResponseEntity<RestUser>  findByEmail(@RequestParam String email ){
       return ResponseEntity.ok(RestUser.fromDomain(service.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("User is not there with this email"))));
    }

    @GetMapping("/all")
    public  ResponseEntity<Page<RestUser>>  findAll(Pageable pageable){
        Page<User> usersPage = service.findAll(pageable); // Page<User> from domain

        // Map domain -> DTO manually
        List<RestUser> dtoList = usersPage.stream()
                .map(RestUser::fromDomain)
                .toList();

        // Wrap in PageImpl to keep pagination info
        Page<RestUser> dtoPage = new PageImpl<>(
                dtoList,
                pageable,
                usersPage.getTotalElements()
        );

        return ResponseEntity.ok(dtoPage);
    }

@PostMapping
    public ResponseEntity<String> saveUser(@RequestBody  RestUser user){
    return service.saveUser(RestUser.fromDomain(user));
}


}

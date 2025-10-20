package com.lnr.authentication_service.user.application;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.profile.service.UserService;
import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserService userService;


    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return  userService.findByEmail(new UserEmail(email));

    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable){
        return userService.findAll(pageable);
    }


    @Transactional
    public void saveUser(User newUser){
        userService.save(newUser);
    }



}

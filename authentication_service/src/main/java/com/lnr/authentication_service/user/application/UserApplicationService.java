package com.lnr.authentication_service.user.application;

import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfile;
import com.lnr.authentication_service.user.domain.profile.repository.IUserRepository;
import com.lnr.authentication_service.user.domain.profile.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserApplicationService {

    private final UserService userService;

    private  final IUserRepository userRepository;

    public UserApplicationService(IAccountRepository accountRepository, IUserRepository userRepository) {
         this.userRepository = userRepository;
        this.userService = new UserService(userRepository);

    }



    //
//    @Transactional(readOnly = true)
//    public Optional<UserProfile> findByEmail(String email) {
//        return  userService.findByEmail(new UserEmail(email));
//
//    }

//    @Transactional(readOnly = true)
//    public Page<UserProfile> findAll(Pageable pageable){
//        return userService.findAll(pageable);
//    }
//
//
//    @Transactional
//    public void saveUser(UserProfile newUser){
//        userService.save(newUser);
//    }



}

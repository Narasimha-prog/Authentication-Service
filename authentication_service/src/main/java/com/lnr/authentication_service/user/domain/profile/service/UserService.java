package com.lnr.authentication_service.user.domain.profile.service;


import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.repository.IUserRepository;
import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.user.domain.profile.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;


     public Optional<User> findByEmail(UserEmail userEmail){
        return userRepository.findByEmail(userEmail);
    }


    public Optional<User> findfByPublicId(UserPublicId publicId){
         return userRepository.get(publicId);
    }

   public Page<User> findAll(Pageable pageable){
         return userRepository.findAll(pageable);
   }

   public void save(User newUser){
         userRepository.save(newUser);
   }

}

package com.lnr.authentication_service.user.domain.profile.service;


import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.UserProfileIsNotFound;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfile;
import com.lnr.authentication_service.user.domain.profile.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;


//     public Optional<UserProfile> findByEmail(UserEmail userEmail){
//
//         return userRepository.findByEmail(userEmail);
//    }
//
//
//    public Optional<UserProfile> findfByPublicId(UserPublicId publicId){
//         return userRepository.get(publicId);
//    }
//
//   public Page<UserProfile> findAll(Pageable pageable){
//         return userRepository.findAll(pageable);
//   }
//
//   public void save(UserProfile newUser){
//         userRepository.save(newUser);
//   }

}

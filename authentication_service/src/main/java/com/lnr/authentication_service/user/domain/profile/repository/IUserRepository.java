package com.lnr.authentication_service.user.domain.profile.repository;

import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserRepository {
  void save(UserProfile user);// store user in DB
   Optional<UserProfile> get(UserPublicId publicId);
  Optional<UserProfile>findByEmail(UserEmail email);// fetch user by email for login
//    Page<UserProfile> findAll(Pageable pageable);
}

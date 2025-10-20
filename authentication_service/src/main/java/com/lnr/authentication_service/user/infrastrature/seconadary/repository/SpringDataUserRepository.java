package com.lnr.authentication_service.user.infrastrature.seconadary.repository;

import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.UserProfileIsNotFound;
import com.lnr.authentication_service.user.domain.profile.aggrigate.UserProfile;
import com.lnr.authentication_service.user.domain.profile.repository.IUserRepository;
import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataUserRepository implements IUserRepository {

    private final JpaUserRepository jpaUserRepository;


    @Override
    public void save(UserProfile user) {
      UserProfileEntity.toDomain(jpaUserRepository.save(UserProfileEntity.toEntity(user)));

    }

    @Override
    public Optional<UserProfile> get(UserPublicId publicId) {
        return  jpaUserRepository.findByPublicId(publicId.value()).map(UserProfileEntity::toDomain);
    }

    @Override
    public Optional<UserProfile> findByEmail(UserEmail email) {
        return Optional.of(UserProfileEntity.toDomain(jpaUserRepository.findByEmail(email.value()).orElseThrow(() -> new UserProfileIsNotFound("UserProfile is not there with this Email"))));
    }
//
//    @Override
//    public Page<UserProfile> findAll(Pageable  pageable) {
//        return   jpaUserRepository.findAll(pageable).map(UserProfileEntity::toDomain);
//    }
}

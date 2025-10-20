package com.lnr.authentication_service.user.infrastrature.seconadary.repository;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.repository.IUserRepository;
import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.user.domain.profile.vo.UserPublicId;
import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
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
    public void save(User user) {
      UserEntity.toDomain(jpaUserRepository.save(UserEntity.toEntity(user)));

    }

    @Override
    public Optional<User> get(UserPublicId publicId) {
        return  jpaUserRepository.findByPublicId(publicId.value()).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(UserEmail email) {
        return Optional.of(UserEntity.toDomain(jpaUserRepository.findByEmail(email.value()).orElseThrow(() -> new EntityNotFoundException("Entity is not found with email.."))));
    }

    @Override
    public Page<User> findAll(Pageable  pageable) {
        return   jpaUserRepository.findAll(pageable).map(UserEntity::toDomain);
    }
}

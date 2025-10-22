package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class SpringDataAccountRepository implements IAccountRepository {

    private final IJpaAccountRepository accountRepository;


    @Override
    public Optional<UserAccount> findByPublicId(UserPublicId publicId) {

     return accountRepository.findByPublicId(publicId.value()).map(UserAccountEntity::toDomain);

    }

    @Override
    public Optional<UserAccount> findByEmail(UserEmail userEmail) {
        return accountRepository.findByEmail(userEmail.value()).map(UserAccountEntity::toDomain);
    }

    @Override
    public UserAccount save(UserAccount account) {
        return UserAccountEntity.toDomain(accountRepository.save(UserAccountEntity.fromDomain(account)));
    }
}

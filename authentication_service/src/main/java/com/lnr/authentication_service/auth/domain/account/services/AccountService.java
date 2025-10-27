package com.lnr.authentication_service.auth.domain.account.services;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.auth.domain.account.vo.AuthTokens;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AccountService {

    private final IAccountRepository accountRepository;




    public Optional<UserAccount> findByPublicId(UserPublicId publicId){
        return accountRepository.findByPublicId(publicId);
    }

    public Optional<UserAccount> findByEmail(UserEmail userEmail) {
        return accountRepository.findByEmail(userEmail);
    }


    public UserAccount save(UserAccount  account){
     return  accountRepository.save(account);

    }
}

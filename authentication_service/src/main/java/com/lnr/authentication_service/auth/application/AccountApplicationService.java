package com.lnr.authentication_service.auth.application;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.auth.domain.account.services.AccountService;
import com.lnr.authentication_service.auth.domain.account.services.ITokenService;
import com.lnr.authentication_service.auth.infrastructure.primary.service.RefreshTokenService;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.UserAccountNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class   AccountApplicationService {


    private final AccountService service;



    public AccountApplicationService(RefreshTokenService tokenService, IAccountRepository accountRepository) {

        this.service = new AccountService(accountRepository);

    }

    @Transactional
    public UserAccount RegisterAccount(UserAccount account){
        return  service.save(account);
    }

    @Transactional(readOnly = true)
    public UserAccount findAccountByPublicId(UserPublicId publicId){
        return service.findByPublicId(new UserPublicId(publicId.value())).orElseThrow(()->new UserAccountNotFound("UserAccount is not there for this id"));
    }



    @Transactional(readOnly = true)
    public UserAccount findAccountByEmail(UserEmail userEmail) {
        return service
                .findByEmail(userEmail).orElseThrow(()->new UserAccountNotFound("UserAccount is not there for this Email"));
    }

//@Transactional(readOnly = true)
//  public RefreshToken findTokenByPublicId(UserPublicId publicId){
//        return tokenService.findByPublicId(publicId).orElseThrow(()->new RefreshTokenNotFound("RefreshToken is not there for this id"));
//  }

}

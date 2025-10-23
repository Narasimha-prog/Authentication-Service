package com.lnr.authentication_service.auth.application;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.auth.domain.account.services.AccountService;
import com.lnr.authentication_service.auth.domain.account.services.IAuthService;
import com.lnr.authentication_service.auth.domain.account.services.ITokenService;
import com.lnr.authentication_service.auth.domain.account.vo.AuthTokens;
import com.lnr.authentication_service.auth.infrastructure.primary.dto.RestAuthTokens;
import com.lnr.authentication_service.auth.infrastructure.primary.service.RefreshTokenService;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.RefreshTokenNotFound;
import com.lnr.authentication_service.shared.error.domain.UserAccountNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountApplicationService {


    private final AccountService service;

    private final ITokenService tokenService;

    private final IAccountRepository accountRepository ;

    private  final IAuthService authService;

    public AccountApplicationService(RefreshTokenService tokenService, IAccountRepository accountRepository, IAuthService authService) {
        this.tokenService = tokenService;
        this.accountRepository = accountRepository;
        this.authService = authService;
        this.service = new AccountService(accountRepository,authService);

    }

    @Transactional
    public AuthTokens saveAccount(UserAccount account){
       return   service.save(account);
    }

    @Transactional(readOnly = true)
    public UserAccount findAccountByPublicId(UUID publicId){
        return service.findByPublicId(new UserPublicId(publicId)).orElseThrow(()->new UserAccountNotFound("UserAccount is not there for this id"));
    }


    @Transactional(readOnly = true)
    public UserAccount findAccountByEmail(UserEmail userEmail) {
        return service
                .findByEmail(userEmail).orElseThrow(()->new UserAccountNotFound("UserAccount is not there for this Email"));
    }

@Transactional(readOnly = true)
  public RefreshToken findTokenByPublicId(UserPublicId publicId){
        return tokenService.findByPublicId(publicId).orElseThrow(()->new RefreshTokenNotFound("RefreshToken is not there for this id"));
  }
}

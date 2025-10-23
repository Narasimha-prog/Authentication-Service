package com.lnr.authentication_service.auth.domain.account.services;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.AuthTokens;


    public interface IAuthService {
        AuthTokens generateTokensFor(UserAccount user);
    }


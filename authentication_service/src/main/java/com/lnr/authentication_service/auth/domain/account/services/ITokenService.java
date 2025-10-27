package com.lnr.authentication_service.auth.domain.account.services;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface ITokenService {

    void revokeTokensByUser(UserPublicId userPublicId);

    void revokeToken(RefreshPublicId tokenPublicId);

    void saveTokens(UserAccount account);

    Optional<RefreshToken> findByPublicId(RefreshPublicId publicId);

    Optional<RefreshToken> findByToken(String token);
}

package com.lnr.authentication_service.auth.domain.account.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface IRefreshTokenRepository {

    /** Save a new refresh token */
    RefreshToken save(RefreshToken refreshToken, UserAccount account);

    /** Find a refresh token by token string */
    Optional<RefreshToken> findByPublicId(UserPublicId publicId);

    /** Delete a refresh token */
    void delete(RefreshToken refreshToken);

    /** Delete all refresh tokens for a given user */
    void deleteAllByUser(UserPublicId userId);

    /** Check if a refresh token exists */
    boolean existsByToken(String token);
}

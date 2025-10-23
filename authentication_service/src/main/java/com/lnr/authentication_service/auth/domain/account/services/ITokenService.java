package com.lnr.authentication_service.auth.domain.account.services;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface ITokenService {
 public RefreshToken createRefreshToken(UserAccount account);

Optional<RefreshToken> findByPublicId(UserPublicId publicId);
}

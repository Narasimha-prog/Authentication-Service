package com.lnr.authentication_service.auth.domain.account.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.util.Optional;

public interface IAccountRepository {

   Optional<UserAccount> findByPublicId(UserPublicId publicId);

   Optional<UserAccount>  findByEmail(UserEmail userEmail);


   UserAccount save(UserAccount account);
}

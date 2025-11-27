package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.AccountDbId;
import com.lnr.authentication_service.auth.domain.account.vo.UserLastSeen;
import com.lnr.authentication_service.auth.domain.account.vo.UserPassword;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import org.jilt.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.Instant;
import java.util.Set;


@Builder
public record RestAccount(
        String email,
        String password
) {



    public static UserAccount toDomain(RestAccount dto, PasswordEncoder encoder, UserPublicId publicId) {
        String hashed = encoder.encode(dto.password()); // infrastructure hashes
        UserPassword passwordVO = new UserPassword(hashed);


      return
              new UserAccount(
                      publicId,
                      new com.lnr.authentication_service.shared.domain.user.vo.UserEmail(dto.email)
                      ,passwordVO
                      ,new UserLastSeen(Instant.now()), Set.of());


    }
}

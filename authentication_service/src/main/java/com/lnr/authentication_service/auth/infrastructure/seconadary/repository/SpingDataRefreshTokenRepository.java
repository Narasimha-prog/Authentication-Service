package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IAccountRepository;
import com.lnr.authentication_service.auth.domain.account.repository.IRefreshTokenRepository;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpingDataRefreshTokenRepository implements IRefreshTokenRepository {

  private final IJpaRefreshTokenRepository refreshTokenRepository;

  private final IJpaAccountRepository  accountRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken, UserAccount account) {
// ✅ Create the refresh token entity
        RefreshTokenEntity tokenEntity = RefreshTokenEntity.fromDomain(refreshToken);

        // ✅ Fetch the managed user entity (so Hibernate knows it's persisted)
        UserAccountEntity accountEntity = accountRepository
                .findByPublicId(account.getPublicId().value())
                .orElseThrow(() -> new IllegalStateException("User not found for refresh token"));




        // 3️⃣ Link user <-> token
        tokenEntity.setUser(accountEntity);
        accountEntity.getRefreshTokens().add(tokenEntity);

        // 4️⃣ Persist refresh token
        RefreshTokenEntity saved = refreshTokenRepository.save(tokenEntity);

        // 5️⃣ Return as domain object
        return RefreshTokenEntity.toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public void delete(RefreshToken refreshToken) {

    }

    @Override
    public void deleteAllByUser(UserPublicId userId) {

    }

    @Override
    public boolean existsByToken(String token) {
        return false;
    }
}

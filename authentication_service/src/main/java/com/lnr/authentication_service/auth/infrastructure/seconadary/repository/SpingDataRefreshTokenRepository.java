package com.lnr.authentication_service.auth.infrastructure.seconadary.repository;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.repository.IRefreshTokenRepository;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.UserAccountEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpingDataRefreshTokenRepository implements IRefreshTokenRepository {

    private final AccountApplicationService applicationService;

  private final IJpaRefreshTokenRepository refreshTokenRepository;

    @Override
    public List<RefreshTokenEntity> findAllByUserPublicId(UserPublicId userPublicId) {
        return List.of();
    }

    private final IJpaAccountRepository  accountRepository;





    @Override
    public RefreshToken save(RefreshToken refreshToken, UserAccount account) {

// ✅ Create the refresh token entity
        RefreshTokenEntity tokenEntity = RefreshTokenEntity.fromDomain(refreshToken,account);

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
    public Optional<RefreshToken> findByPublicId(RefreshPublicId publicId) {
        return refreshTokenRepository.findByPublicId(publicId.value()).map(RefreshTokenEntity::toDomain);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token).map(RefreshTokenEntity::toDomain);
    }




    @Override
    public void deleteAllByUser(UserPublicId userId) {
      refreshTokenRepository.deleteAllByUserPublicId(userId.value());
    }

    @Override
    public boolean existsByToken(String token) {
        return findByToken(token).isPresent();
    }

    @Override
    public void deleteByPublicId(RefreshPublicId publicId) {
        refreshTokenRepository.deleteByPublicId(publicId.value());
    }


}

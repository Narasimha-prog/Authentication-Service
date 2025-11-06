package com.lnr.authentication_service.auth.domain.account.repository;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RefreshTokenEntity;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.util.List;
import java.util.Optional;



        public interface IRefreshTokenRepository {

            RefreshToken save(RefreshToken refreshToken, UserAccount account,RefreshPublicId refreshPublicId);

            Optional<RefreshToken> findByPublicId(RefreshPublicId publicId);

            Optional<RefreshToken> findByToken(String token);

            boolean existsByToken(String token);

            void deleteByPublicId(RefreshPublicId publicId);

            void deleteAllByUser(UserPublicId userId);

            List<RefreshTokenEntity> findAllByUserPublicId(UserPublicId userPublicId);
        }


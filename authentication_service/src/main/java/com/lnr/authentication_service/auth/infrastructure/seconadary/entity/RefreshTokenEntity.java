package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;

import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "refresh_token_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID publicId; // DDD friendly external ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // ✅ link directly to user
    private UserAccountEntity user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    // --- Mappers ---
    public com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken toDomain() {
        return new com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken(
                token,
                new UserPublicId(user.getPublicId()),
                expiryDate
        );
    }

    public static RefreshTokenEntity fromDomain(com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken domain) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setPublicId(UUID.randomUUID());
        entity.setToken(domain.getToken());
        entity.setExpiryDate(domain.getExpiryDate());
        // user should be set by caller: entity.setUser(userEntity);
        return entity;
    }
}


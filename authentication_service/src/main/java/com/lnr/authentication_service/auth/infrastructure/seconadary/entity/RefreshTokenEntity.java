package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;

import com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshDbId;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RefreshTokenEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "refresh_token_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private UUID publicId; // DDD friendly external ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // ✅ link directly to user
    private UserAccountEntity user;

    @Column(nullable = false, unique = true,length = 2048)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean revoked = false;

    // --- Mappers ---

    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        return new RefreshToken(
                new RefreshPublicId(entity.getPublicId()),        // maps UUID → value object
                entity.getToken(),                                // maps token string
                new UserPublicId(entity.getUser().getPublicId()), // maps user
                entity.getExpiryDate(),                           // maps expiry
                false,                                            // revoked flag (if not persisted yet)
                new RefreshDbId(entity.getId())                   // maps DB ID
        );

    }


    public static RefreshTokenEntity fromDomain(RefreshToken domain, UserAccount userAccount) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setPublicId(UUID.randomUUID());
        entity.setToken(domain.getToken());
        entity.setExpiryDate(domain.getExpiryDate());
        entity.setUser(UserAccountEntity.fromDomain(userAccount));
        entity.setId(domain.getDbId() == null ? null : entity.getId());
        entity.setRevoked(domain.isRevoked() && entity.isRevoked());
        return entity;
    }
}


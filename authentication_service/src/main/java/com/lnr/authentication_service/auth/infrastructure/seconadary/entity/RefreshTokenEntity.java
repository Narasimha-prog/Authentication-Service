package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;

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

    // --- Mappers ---
    public static com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }

        return new com.lnr.authentication_service.auth.domain.account.aggrigate.RefreshToken(
                entity.getToken(),
                new UserPublicId(entity.getUser().getPublicId()),
                entity.getExpiryDate()
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


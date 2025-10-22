package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityName;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserProfileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AuthorityEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_user_seq")
    @SequenceGenerator(name = "auth_user_seq", sequenceName = "auth_user_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private AuthorityName  name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccountEntity user;

    @Column(nullable = false)
    private boolean enabled = true;

    // --- Mappers ---
    public com.lnr.authentication_service.auth.domain.account.aggrigate.Authority toDomain() {
        return new com.lnr.authentication_service.auth.domain.account.aggrigate.Authority(name);
    }

    public static AuthorityEntity fromDomain(com.lnr.authentication_service.auth.domain.account.aggrigate.Authority authority) {
        return AuthorityEntity.builder()
                .publicId(UUID.randomUUID())
                .name(authority.getName())
                .enabled(true)
                .build();
    }

}

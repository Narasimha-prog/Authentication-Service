package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.RoleName;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RoleEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private RoleName name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccountEntity user; // Owner reference

    @OneToMany(orphanRemoval = false,cascade = CascadeType.ALL)
    private Set<AuthorityEntity> authorities;

    // --- Mappers ---
    public com.lnr.authentication_service.auth.domain.account.aggrigate.Role toDomain() {
        Set<com.lnr.authentication_service.auth.domain.account.aggrigate.Authority> auths = authorities.stream()
                .map(AuthorityEntity::toDomain)
                .collect(Collectors.toSet());
        return new com.lnr.authentication_service.auth.domain.account.aggrigate.Role(name, auths);
    }

    public static RoleEntity fromDomain(com.lnr.authentication_service.auth.domain.account.aggrigate.Role roleDomain) {
        Set<AuthorityEntity> authorityEntities = roleDomain.getAuthorities().stream()
                .map(AuthorityEntity::fromDomain)
                .collect(Collectors.toSet());

        RoleEntity roleEntity = RoleEntity.builder()
                .publicId(UUID.randomUUID())
                .name(roleDomain.getName())
                .authorities(authorityEntities)
                .build();

        // Link each authority back to role
        authorityEntities.forEach(auth -> auth.setRole(roleEntity));

        return roleEntity;
    }
}

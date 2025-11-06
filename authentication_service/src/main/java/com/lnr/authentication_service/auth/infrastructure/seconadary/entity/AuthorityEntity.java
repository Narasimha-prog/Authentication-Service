package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityName;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import com.lnr.authentication_service.user.infrastrature.seconadary.entity.UserProfileEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class AuthorityEntity extends AbstractAuditingEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // internal database primary key

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private AuthorityName  name;


    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<RoleEntity> roles = new HashSet<>();

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

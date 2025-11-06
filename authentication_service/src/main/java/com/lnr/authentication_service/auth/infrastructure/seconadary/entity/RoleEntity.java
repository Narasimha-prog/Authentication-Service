package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Authority;
import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.vo.RoleDbId;
import com.lnr.authentication_service.auth.domain.account.vo.RoleName;
import com.lnr.authentication_service.auth.domain.account.vo.RolePublicId;
import com.lnr.authentication_service.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
    private Long id;   // internal database primary key

    @EqualsAndHashCode.Include
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private RoleName name;



    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<UserAccountEntity> users;  // Owner reference


@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
@JoinTable(name = "role_authorities",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
private Set<AuthorityEntity> authorities = new HashSet<>();


    // --- Mappers ---

    public Role toDomain() {
        RolePublicId publicId = new RolePublicId(this.publicId);
        Set<Authority> auths = authorities.stream()
                .map(AuthorityEntity::toDomain)
                .collect(Collectors.toSet());
        return new Role(publicId,name, auths,new RoleDbId(id));
    }

    public static RoleEntity fromDomain(Role roleDomain) {
      //get authorities from role
        Set<AuthorityEntity> authorityEntities = roleDomain.getAuthorities().stream()
                .map(AuthorityEntity::fromDomain)
                .collect(Collectors.toSet());

       //build role entity by using authorities
        RoleEntity roleEntity = RoleEntity.builder()
                .publicId(roleDomain.getPublicId().value())
                .name(roleDomain.getName())
                .authorities(authorityEntities)
                .build();

        // Link each authority back to role
        authorityEntities.forEach(auth -> auth.setRoles(Set.of(roleEntity)));

        return roleEntity;
    }
}

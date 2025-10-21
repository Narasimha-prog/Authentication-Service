package com.lnr.authentication_service.auth.infrastructure.seconadary.entity;


import com.lnr.authentication_service.auth.domain.account.vo.*;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_accounts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UserAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "db_id", nullable = false, updatable = false)
    private Long dbId; // internal database primary key

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    private UUID publicId; // persisted as string, represents UserPublicId

    @Column(name = "email", nullable = false, unique = true)
    private String email; // persisted as string, represents UserEmail

    @Column(name = "password", nullable = false)
    private String password; // persisted as string, represents UserPassword

    @Column(name = "last_seen", nullable = false)
    private Instant lastSeen; // persisted as timestamp, represents UserLastSeen

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RefreshTokenEntity> refreshTokens = new HashSet<>();




    // --- Constructors ---
    public UserAccountEntity(UUID publicId,
                             String email,
                             String password,
                             Instant lastSeen,
                             Set<RoleEntity> roles) {
        this.publicId = publicId;
        this.email = email;
        this.password = password;
        this.lastSeen = lastSeen;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
    }

    public UserAccountEntity(UUID publicId, String email, String password) {
        this.publicId = publicId != null ? publicId : UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.lastSeen = Instant.now();

        // Default authority
        AuthorityEntity defaultAuthority = new AuthorityEntity();
        defaultAuthority.setName(AuthorityName.USER_READ);

        // Default role
        RoleEntity defaultRole = new RoleEntity();
        defaultRole.setName(RoleName.USER);
        defaultRole.setUser(this);
        defaultRole.setAuthorities(Set.of(defaultAuthority));

        // Link authority to role
        defaultAuthority.setRole(defaultRole);

        // Add role to user
        this.roles.add(defaultRole);
    }



    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void markLastSeen(Instant timestamp) {
        this.lastSeen = timestamp;
    }

    public void addRole(RoleEntity role) {
        this.roles.add(role);
    }

    public void removeRole(RoleName roleName) {
        this.roles.removeIf(role -> role.getName() == roleName);
    }

    public Set<RoleEntity> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    // --- Mappers ---
    public com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount toDomain() {
        Set<com.lnr.authentication_service.auth.domain.account.aggrigate.Role> domainRoles = roles.stream()
                .map(RoleEntity::toDomain)
                .collect(Collectors.toSet());

        return new com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount(
                new UserPublicId(publicId),
                new UserEmail(email),
                new UserPassword(password),
                new UserLastSeen(lastSeen),
                domainRoles,
                new AccountDbId(dbId)
        );
    }

    public static UserAccountEntity fromDomain(com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount domain) {
        Set<RoleEntity> roleEntities = domain.getRoles().stream()
                .map(RoleEntity::fromDomain)
                .collect(Collectors.toSet());

        UserAccountEntity entity = new UserAccountEntity();
        entity.setPublicId(domain.getPublicId().value());
        entity.setEmail(domain.getEmail().value());
        entity.setPassword(domain.getPassword().value());
        entity.setLastSeen(domain.getLastSeen().value());
        entity.setRoles(roleEntities);

        // Link each role back to user
        roleEntities.forEach(role -> role.setUser(entity));

        return entity;
    }
}

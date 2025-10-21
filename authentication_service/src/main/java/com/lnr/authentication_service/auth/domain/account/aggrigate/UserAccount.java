package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.*;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class UserAccount {

    @EqualsAndHashCode.Include
    private final UserPublicId publicId;   // shared across services

    private final UserEmail email;         // login identifier

    private final UserPassword password;   // hashed credentials

    private final UserLastSeen lastSeen;   // security/audit info

    private final Set<Role> roles;         // roles for authorization

    private final AccountDbId dbId;        // local DB PK (internal only)

    // ------------------------
    // All-args constructor
    // ------------------------
    public UserAccount(UserPublicId publicId,
                       UserEmail email,
                       UserPassword password,
                       UserLastSeen lastSeen,
                       Set<Role> roles,
                       AccountDbId dbId) {
        assertAllFields(publicId, email, password, lastSeen, dbId);
        this.publicId = publicId;
        this.email = email;
        this.password = password;
        this.lastSeen = lastSeen;
        this.roles = roles == null ? Set.of() : Set.copyOf(roles); // immutable copy
        this.dbId = dbId;
    }

    // ------------------------
    // Convenience constructor for new accounts
    // ------------------------
    public UserAccount(UserPublicId publicId,
                       UserEmail email,
                       UserPassword password,
                       UserLastSeen lastSeen,
                       Set<Role> roles) {
        this(publicId, email, password, lastSeen, roles, new AccountDbId(0L));
    }

    // ------------------------
    // Minimal constructor with default role & authority
    // ------------------------
    public UserAccount(UserPublicId publicId,
                       UserEmail email,
                       UserPassword password,
                       UserLastSeen lastSeen) {
        this(
                publicId,
                email,
                password,
                lastSeen,
                Set.of(
                        new Role(
                                RoleName.USER,
                                Set.of(new Authority(AuthorityName.USER_READ))
                        )
                ),
                new AccountDbId(0L)
        );
    }

    // ------------------------
    // Validation
    // ------------------------
    private void assertAllFields(UserPublicId publicId, UserEmail email,
                                 UserPassword password, UserLastSeen lastSeen,
                                 AccountDbId dbId) {
        Assert.notNull("UserPublicId", publicId);
        Assert.notNull("UserEmail", email);
        Assert.notNull("UserPassword", password);
        Assert.notNull("UserLastSeen", lastSeen);
        Assert.notNull("UserAccountDbId", dbId);
    }

    // ------------------------
    // Business methods
    // ------------------------
    public UserAccount addRole(Role role) {
        Set<Role> updatedRoles = new java.util.HashSet<>(Set.copyOf(this.roles));
        updatedRoles.add(role);
        return new UserAccount(publicId, email, password, lastSeen, updatedRoles, dbId);
    }
}

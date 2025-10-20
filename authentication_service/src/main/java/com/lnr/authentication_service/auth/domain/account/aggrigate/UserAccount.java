package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.shared.error.domain.Assert;
import com.lnr.authentication_service.user.domain.profile.vo.UserDbId;
import com.lnr.authentication_service.user.domain.profile.vo.UserPublicId;
import com.lnr.authentication_service.auth.domain.account.vo.UserEmail;
import com.lnr.authentication_service.auth.domain.account.vo.UserLastSeen;
import com.lnr.authentication_service.auth.domain.account.vo.UserPassword;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

import java.util.Set;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class UserAccount {

    @EqualsAndHashCode.Include
    private final UserPublicId publicId;   // shared across services

    private final UserEmail email;         // login identifier

    private final UserPassword password;   // hashed credentials

    private final UserLastSeen lastSeen;   // security/audit info

    private final Set<Role> roles;     // roles for authorization

    private final long dbId;           // local DB PK (internal only)

    // All-args constructor
    public UserAccount(UserPublicId publicId,
                       UserEmail email,
                       UserPassword password,
                       UserLastSeen lastSeen,
                       Set<Role> roles,
                       long dbId) {
        assertAllFields(publicId, email, password, lastSeen);
        this.publicId = publicId;
        this.email = email;
        this.password = password;
        this.lastSeen = lastSeen;
        this.roles = roles == null ? Set.of() : Set.copyOf(roles); // immutable
        this.dbId = dbId;
    }

    // Convenience constructor for new accounts (dbId = 0)
    public UserAccount(UserPublicId publicId,
                       UserEmail email,
                       UserPassword password,
                       UserLastSeen lastSeen,
                       Set<Role> roles) {
        this(publicId, email, password, lastSeen, roles, 0L);
    }

    private void assertAllFields(UserPublicId publicId, UserEmail email,
                                 UserPassword password, UserLastSeen lastSeen) {
        Assert.notNull("UserPublicId", publicId);
        Assert.notNull("UserEmail", email);
        Assert.notNull("UserPassword", password);
        Assert.notNull("UserLastSeen", lastSeen);
    }
}


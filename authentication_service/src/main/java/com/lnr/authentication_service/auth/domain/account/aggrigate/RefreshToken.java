package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.RefreshDbId;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import com.lnr.authentication_service.shared.error.domain.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class RefreshToken {

    @EqualsAndHashCode.Include
    private final String token;          // unique token string

    private final UserPublicId userId;   // links to UserAccount

    private final Instant expiryDate;    // token expiration

    private boolean revoked;             // flag for invalidation

    private final RefreshDbId dbId;

    // All-args constructor
    public RefreshToken(String token, UserPublicId userId, Instant expiryDate, boolean revoked,RefreshDbId bdId) {
        assertAllFields(token, userId, expiryDate,bdId);
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.dbId=bdId;
    }

    // Convenience constructor for new tokens
    public RefreshToken(String token, UserPublicId userId, Instant expiryDate) {
        this(token, userId, expiryDate, false, new RefreshDbId(0L));
    }

    private void assertAllFields(String token, UserPublicId userId, Instant expiryDate,RefreshDbId dbId) {
        Assert.notNull("Token", token);
        Assert.notNull("UserPublicId", userId);
        Assert.notNull("ExpiryDate", expiryDate);
        Assert.notNull("DbId",dbId);
    }

    // Business method to revoke token
    public void revoke() {
        this.revoked = true;
    }

    // Business method to check expiration
    public boolean isExpired() {
        return Instant.now().isAfter(expiryDate);
    }
}


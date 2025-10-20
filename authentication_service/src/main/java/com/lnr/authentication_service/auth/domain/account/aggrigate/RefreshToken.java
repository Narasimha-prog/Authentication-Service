package com.lnr.authentication_service.auth.domain.token.aggregate;

import com.lnr.authentication_service.shared.error.domain.Assert;
import com.lnr.authentication_service.user.domain.profile.vo.UserPublicId;
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

    private final long dbId;

    // All-args constructor
    public RefreshToken(String token, UserPublicId userId, Instant expiryDate, boolean revoked,long bdId) {
        assertAllFields(token, userId, expiryDate,bdId);
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
        this.dbId=bdId;
    }

    // Convenience constructor for new tokens
    public RefreshToken(String token, UserPublicId userId, Instant expiryDate) {
        this(token, userId, expiryDate, false, 0L);
    }

    private void assertAllFields(String token, UserPublicId userId, Instant expiryDate,long dbId) {
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


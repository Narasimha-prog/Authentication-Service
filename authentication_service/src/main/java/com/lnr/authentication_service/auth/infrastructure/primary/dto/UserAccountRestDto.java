package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST DTO for returning UserAccount info.
 * Password is never exposed.
 */
public record UserAccountRestDto(
        String publicId,
        String email,
        Set<RoleRestDto> roles
) {

    public static UserAccountRestDto fromDomain(UserAccount domain) {
        return new UserAccountRestDto(
                domain.getPublicId().value().toString(),
                domain.getEmail().value(),
                domain.getRoles().stream()
                        .map(RoleRestDto::fromDomain)
                        .collect(Collectors.toSet())
        );
    }

    /**
     * Convert to domain object.
     * Password must be supplied externally when creating/updating a user.
     */
    public UserAccount toDomain(String rawPassword) {
        Set<Role> domainRoles = roles != null
                ? roles.stream().map(RoleRestDto::toDomain).collect(Collectors.toSet())
                : Set.of();

        return new UserAccount(
                new UserPublicId(UUID.fromString(publicId)),
                new UserEmail(email),
                new com.lnr.authentication_service.auth.domain.account.vo.UserPassword(rawPassword),
                new com.lnr.authentication_service.auth.domain.account.vo.UserLastSeen(Instant.now()),
                domainRoles
        );
    }
}

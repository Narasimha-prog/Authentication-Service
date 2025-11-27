package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Authority;
import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.vo.*;
import com.lnr.authentication_service.auth.infrastructure.seconadary.entity.RoleEntity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record AuthorityRestDto(
        String publicId,
        String name,
        Set<String> roles,
        boolean enabled
) {
    public static AuthorityRestDto fromDomain(Authority domain) {
        return new AuthorityRestDto(
                domain.getPublicId().value().toString(),
                domain.getName().name(),
                domain.getRoles().stream()
                        .map(role -> role.getName().name())  // or role publicId, depending on your design
                        .collect(Collectors.toSet()),
                domain.isEnabled()
        );
    }

    public Authority toDomain() {
        return new Authority(
                new AuthorityPublicId(UUID.fromString(publicId)),
                AuthorityName.valueOf(name),
                roles.stream()
                        .map(roleName -> new Role(
                                        new RolePublicId(UUID.randomUUID()),  // or fetched from DB
                                        RoleName.valueOf(roleName),
                                        Set.of() // you can leave roles empty if not needed for now
                                ,new RoleDbId(0L)
                                )).collect(Collectors.toSet()),
                enabled
        );
    }
}

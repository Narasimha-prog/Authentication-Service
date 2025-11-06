package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Authority;
import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;
import com.lnr.authentication_service.auth.domain.account.vo.RoleDbId;
import com.lnr.authentication_service.auth.domain.account.vo.RoleName;
import com.lnr.authentication_service.auth.domain.account.vo.RolePublicId;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record RoleRestDto(String publicId, String name, Set<AuthorityRestDto> authorities) {

    public static RoleRestDto fromDomain(Role domain) {
        return new RoleRestDto(
                domain.getPublicId() != null ? domain.getPublicId().value().toString() : null,
                domain.getName().name(),
                domain.getAuthorities() == null ? Set.of() :
                        domain.getAuthorities().stream()
                                .map(AuthorityRestDto::fromDomain)
                                .collect(Collectors.toSet())
        );
    }

    public Role toDomain() {
        Set<Authority> domainAuthorities =
                authorities == null ? Set.of() :
                        authorities.stream()
                                .map(AuthorityRestDto::toDomain)
                                .collect(Collectors.toSet());

        return new Role(
                new RolePublicId(publicId != null ? UUID.fromString(publicId) : UUID.randomUUID()),
                RoleName.valueOf(name),
                domainAuthorities,
                new RoleDbId(0L)
        );
    }
}

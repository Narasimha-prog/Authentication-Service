package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Role;

import java.util.Set;
import java.util.stream.Collectors;

public record RoleRestDto(String publicId, String name, Set<AuthorityRestDto> authorities) {

    public static RoleRestDto fromDomain(Role domain) {
        return new RoleRestDto(
                domain.getDbId() != null ? domain.getDbId().toString() : null,
                domain.getName().name(),
                domain.getAuthorities().stream()
                        .map(AuthorityRestDto::fromDomain)
                        .collect(Collectors.toSet())
        );
    }

    public Role toDomain() {
        Set<com.lnr.authentication_service.auth.domain.account.aggrigate.Authority> domainAuthorities =
                authorities.stream().map(AuthorityRestDto::toDomain).collect(Collectors.toSet());
        return new Role(
                com.lnr.authentication_service.auth.domain.account.vo.RoleName.valueOf(name),
                domainAuthorities
        );
    }
}

package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Authority;
import com.lnr.authentication_service.auth.domain.account.vo.AuthDbId;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityDbId;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityName;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityPublicId;
import com.lnr.authentication_service.auth.domain.account.vo.RolePublicId;

import java.util.UUID;

public record AuthorityRestDto(String publicId, String name) {

    public static AuthorityRestDto fromDomain(Authority domain) {
        return new AuthorityRestDto(
                domain != null ? domain.getPublicId().value().toString() : null,
                domain.getName().name()

        );
    }

    public Authority toDomain(AuthorityPublicId authorityPublicId) {
        return new Authority(
                new AuthorityPublicId(publicId != null ? UUID.fromString(publicId.value()) : UUID.randomUUID()),
                AuthorityName.valueOf(name),
                new RolePublicId()
        );
    }
}

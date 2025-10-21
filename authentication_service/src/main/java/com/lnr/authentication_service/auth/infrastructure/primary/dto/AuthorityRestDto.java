package com.lnr.authentication_service.auth.infrastructure.primary.dto;

import com.lnr.authentication_service.auth.domain.account.aggrigate.Authority;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityName;

public record AuthorityRestDto(String publicId, String name, boolean enabled) {

    public static AuthorityRestDto fromDomain(Authority domain) {
        return new AuthorityRestDto(
                domain.getDbId() != null ? domain.getDbId().toString() : null,
                domain.getName().name(),
                true // enabled flag
        );
    }

    public Authority toDomain() {
        return new Authority(AuthorityName.valueOf(name));
    }
}

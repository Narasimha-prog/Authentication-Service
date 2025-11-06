package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.*;
import com.lnr.authentication_service.shared.error.domain.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Authority {

    @EqualsAndHashCode.Include
    private final AuthorityPublicId publicId;

    private final AuthorityName name;

    private final RolePublicId rolePublicId;

    // All-args constructor
    public Authority(AuthorityPublicId publicId, AuthorityName name, RolePublicId rolePublicId) {

        assertAllFields(publicId,name,rolePublicId);
        this.publicId = publicId;
        this.rolePublicId = rolePublicId;
        this.name = name;

    }



    private void assertAllFields(AuthorityPublicId publicId,AuthorityName name,RolePublicId rolePublicId) {
        Assert.notNull("AuthorityPublicId",publicId);
        Assert.notNull("AuthorityName", name);
        Assert.notNull("RolePublicId",rolePublicId);
    }
}

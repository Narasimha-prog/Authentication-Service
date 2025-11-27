package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.*;
import com.lnr.authentication_service.shared.error.domain.Assert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jilt.Builder;

import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Authority {

    @EqualsAndHashCode.Include
    private final AuthorityPublicId publicId;

    private final AuthorityName name;

    private final Set<Role> roles;

    private final boolean enabled;

    // All-args constructor
    public Authority(AuthorityPublicId publicId, AuthorityName name, Set<Role> roles,boolean enabled) {


        assertAllFields(publicId,name,roles,enabled);
        this.publicId = publicId;
        this.roles = roles;
        this.name = name;
        this.enabled =enabled;

    }



    private void assertAllFields(AuthorityPublicId publicId,AuthorityName name,Set<Role> roles,boolean enabled) {
        Assert.notNull("AuthorityPublicId",publicId);
        Assert.notNull("AuthorityName", name);
        Assert.notNull("Roles",roles);
        Assert.notNull("Enabled",enabled);
    }
}

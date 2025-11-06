package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.RoleDbId;
import com.lnr.authentication_service.auth.domain.account.vo.RoleName;
import com.lnr.authentication_service.auth.domain.account.vo.RolePublicId;
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
public class Role {

    @EqualsAndHashCode.Include
    private final RolePublicId publicId;


    private final RoleName name;

    private final Set<Authority> authorities;

    private  final RoleDbId dbId;

    // All-args constructor
    public Role(RolePublicId publicId, RoleName name, Set<Authority> authorities, RoleDbId dbId) {

        assertAllFields(publicId,name, authorities,dbId);
        this.publicId = publicId;
        this.name = name;
        this.authorities = Set.copyOf(authorities);
        this.dbId = dbId;
    }


    public void assertAllFields(RolePublicId rolePublicId,RoleName name, Set<Authority> authorities,RoleDbId dbId){
        Assert.notNull("RolePublicId",rolePublicId);
        Assert.notNull("RoleName",name);
        Assert.notNull("Authority Set",authorities);
        Assert.notNull("RoleDbId",dbId);
    }

}

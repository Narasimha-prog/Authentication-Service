package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.RoleDbId;
import com.lnr.authentication_service.auth.domain.account.vo.RoleName;
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
    private final RoleName name;

    private final Set<Authority> authorities;
    private  final RoleDbId dbId;

    // All-args constructor
    public Role(RoleName name, Set<Authority> authorities, RoleDbId dbId) {
        assertAllFields(name, authorities,dbId);
        this.name = name;
        this.authorities = Set.copyOf(authorities); // make immutable
        this.dbId = dbId;
    }

    // Convenience constructor for new roles without dbId
    public Role(RoleName name, Set<Authority> authorities) {
        this(name, authorities, new RoleDbId(0L)); // dbId = 0 indicates not persisted yet
    }

    public void assertAllFields(RoleName name, Set<Authority> authorities,RoleDbId dbId){
        Assert.notNull("RoleName",name);
        Assert.notNull("Authority Set",authorities);
        Assert.notNull("RoleDbId",dbId);
    }

}

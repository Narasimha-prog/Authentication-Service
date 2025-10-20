package com.lnr.authentication_service.auth.domain.account.aggrigate;

import com.lnr.authentication_service.auth.domain.account.vo.AccountDbId;
import com.lnr.authentication_service.auth.domain.account.vo.AuthorityName;
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
    private final AuthorityName name;

    private final AccountDbId dbId;

    // All-args constructor
    public Authority(AuthorityName name, AccountDbId dbId) {
        assertAllFields(name,dbId);
        this.name = name;
        this.dbId = dbId;
    }

    // Convenience constructor for new Authority
    public Authority(AuthorityName name) {
        this(name, new AccountDbId(0L)); // dbId = 0 means not persisted
    }

    private void assertAllFields(AuthorityName name,AccountDbId dbId) {
        Assert.notNull("AuthorityName", name);
        Assert.notNull("AuthorityDatabaseId",dbId);
    }
}

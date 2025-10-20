package com.lnr.authentication_service.auth.domain.account.vo;




import com.lnr.authentication_service.shared.error.domain.Assert;


import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public record Roles(Set<RoleName> roles) {

  public static final Roles EMPTY = new Roles(null);

  public Roles(Set<RoleName> roles) {
    this.roles = Collections.unmodifiableSet(roles);
  }

  public boolean hasRole() {
    return !roles.isEmpty();
  }

  public boolean hasRole(RoleName role) {
    Assert.notNull("role", role);

    return roles.contains(role);
  }


  public Stream<RoleName> stream() {
    return get().stream();
  }

  public Set<RoleName> get() {
    return roles();
  }
}

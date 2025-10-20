package com.lnr.authentication_service.auth.domain.account.vo;

import com.lnr.authentication_service.shared.error.domain.Assert;



import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoleName {
  ADMIN,
  USER,
  ANONYMOUS,
  UNKNOWN;

  private static final String PREFIX = "ROLE_";
  private static final Map<String, RoleName> ROLES = buildRoles();

  private static Map<String, RoleName> buildRoles() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(RoleName::key, Function.identity()));
  }

  public String key() {
    return PREFIX + name();
  }

  public static RoleName from(String role) {
    Assert.notBlank("role", role);

    return ROLES.getOrDefault(role, UNKNOWN);
  }
}

package com.lnr.authentication_service.auth.infrastructure.primary.data;

import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class UserAccountDetails implements UserDetails {

    private final UserAccount user;

    public UserAccountDetails(UserAccount user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .flatMap(role -> role.getAuthorities().stream())
                .map(auth -> (GrantedAuthority) new SimpleGrantedAuthority(auth.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword().value();
    }

    @Override
    public String getUsername() {
        System.out.println("Email = " + user.getEmail());
        System.out.println("Email.value = " + (user.getEmail() != null ? user.getEmail().value() : "NULL"));
        return user.getEmail() != null ? user.getEmail().value() : null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserAccount getDomainUser() {
        return user;
    }
}

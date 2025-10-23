package com.lnr.authentication_service.auth.infrastructure.primary.service;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.shared.domain.user.vo.UserEmail;
import com.lnr.authentication_service.wire.security.infrastracture.primary.data.UserAccountDetailsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAccountDetailsService  implements UserDetailsService {

    private final AccountApplicationService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount user = accountService.findAccountByEmail(new UserEmail(username));
        return new UserAccountDetailsAdapter(user);
    }

}

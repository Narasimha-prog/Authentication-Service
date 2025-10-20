package com.lnr.authentication_service.user.application;

import com.lnr.authentication_service.user.domain.user.aggrigate.User;
import com.lnr.authentication_service.user.domain.user.repository.IUserRepository;
import com.lnr.authentication_service.user.domain.user.vo.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final IUserRepository userRepository;


    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return   userRepository.findByEmail(new UserEmail(email) );

    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }


}

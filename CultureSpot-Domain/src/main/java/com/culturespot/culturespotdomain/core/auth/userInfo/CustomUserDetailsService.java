package com.culturespot.culturespotdomain.core.auth.userInfo;

import com.culturespot.culturespotcommon.global.exception.AuthException;
import com.culturespot.culturespotcommon.global.exception.AuthExceptionCode;
import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}

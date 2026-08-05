package com.sang.ecommerce.service.impl;

import com.sang.ecommerce.entity.Role;
import com.sang.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        var authorities = user.getRoles().stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new).toList();

        return User.withUsername(user.getEmail())
                .password(null)
                .authorities(authorities)
                .build();
    }
}

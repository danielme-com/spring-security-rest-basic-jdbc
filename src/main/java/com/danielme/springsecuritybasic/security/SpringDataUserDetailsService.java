package com.danielme.springsecuritybasic.security;

import com.danielme.springsecuritybasic.model.User;
import com.danielme.springsecuritybasic.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SpringDataUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found with Spring Data"));
    }

    private UserDetails map(User user) {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRol());
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }

}

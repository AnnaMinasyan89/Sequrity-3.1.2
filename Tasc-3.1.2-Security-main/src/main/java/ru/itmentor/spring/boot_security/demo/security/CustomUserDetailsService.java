package ru.itmentor.spring.boot_security.demo.security;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ru.itmentor.spring.boot_security.demo.service.UserService;

@Component
@EnableWebSecurity
public class CustomUserDetailsService implements AuthenticationProvider {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String username = authentication.getName();
            UserDetails userDetails = userService.loadUserByUsername(username);
            String password = authentication.getCredentials().toString();
            if (!password.equals(userDetails.getPassword())) {
                throw new BadCredentialsException("Incorrect password!");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

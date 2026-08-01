package com.sang.ecommerce.security;

import ch.qos.logback.core.util.StringUtil;
import com.sang.ecommerce.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestToken = request.getHeader("Authorization");

        String username = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            var token = requestToken.substring(7);
            username = jwtUtils.extractUsername(token);
        }

        if (StringUtil.notNullNorEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var user = userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(requestToken.substring(7), user)) {
                var authentication = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

package com.github.maitmus.pcgspring.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maitmus.pcgspring.common.constant.EntityStatus;
import com.github.maitmus.pcgspring.common.constant.Role;
import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.common.dto.CommonErrorResponse;
import com.github.maitmus.pcgspring.user.v1.dto.UserDetails;
import com.github.maitmus.pcgspring.validator.JwtTokenValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws IOException {
        String accessToken = request.getHeader(TokenType.ACCESS.getValue());

        try {
            if (accessToken == null || accessToken.isBlank()) {
                throw new Exception("Token not found");
            }

            if (!accessToken.startsWith("Bearer")) {
                throw new Exception("Invalid access token");
            }

            accessToken = accessToken.substring("Bearer ".length());

            Claims claims = jwtTokenValidator.validateToken(accessToken);

            if (claims != null) {
                String username = claims.getSubject();
                Long id = claims.get("id", Long.class);
                String name = claims.get("name", String.class);
                String nickname = claims.get("nickname", String.class);
                String email = claims.get("email", String.class);
                List<?> rawRoles = claims.get("roles", List.class);
                List<String> rolesString = rawRoles.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .toList();
                List<Role> roles = rolesString.stream().map(Role::valueOf).toList();
                String statusString = claims.get("status", String.class);
                EntityStatus status = EntityStatus.valueOf(statusString);
                String rawBirth = claims.get("birth", String.class);
                String rawCreatedAt = claims.get("createdAt", String.class);
                String rawUpdatedAt = claims.get("updatedAt", String.class);

                LocalDate birth = LocalDate.parse(rawBirth);
                LocalDateTime createdAt = LocalDateTime.parse(rawCreatedAt);
                LocalDateTime updatedAt = LocalDateTime.parse(rawUpdatedAt);

                UserDetails userDetails =
                    new UserDetails(id, name, nickname, email, username, birth, roles, status, createdAt, updatedAt);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    roles.stream().map(role ->
                            new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList())
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                    new CommonErrorResponse(HttpStatus.BAD_REQUEST, "Unauthorized")
                )
            );
        }
    }
}

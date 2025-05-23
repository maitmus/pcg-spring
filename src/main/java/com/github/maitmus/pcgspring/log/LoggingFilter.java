package com.github.maitmus.pcgspring.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api/health") && response.getStatus() == 200) {
            return;
        }

        long startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String fullUri = (query == null) ? uri : uri + "?" + query;

        log.info("[REQUEST] {} {} from {}, User-Agent : {}, Content-Type : {}", method,
            fullUri,
            request.getRemoteAddr(),
            request.getHeader("User-Agent"),
            request.getHeader("Content-Type")
        );

        filterChain.doFilter(request, response);

        int status = response.getStatus();
        long duration = System.currentTimeMillis() - startTime;

        log.info("[RESPONSE] {} {} - Status: {} in {}ms", method, fullUri, status, duration);
    }
}
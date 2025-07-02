package com.frol97.codes.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        long duration = System.currentTimeMillis() - startTime;

        logRequest(wrappedRequest);
        logResponse(wrappedResponse, duration);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        log.info("Request: {} {}?{}",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString());
    }

    private void logResponse(ContentCachingResponseWrapper response, long duration) {
        try {
        log.info("Response: {} - {}ms",
                response.getStatus(),
                duration);
        response.copyBodyToResponse();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

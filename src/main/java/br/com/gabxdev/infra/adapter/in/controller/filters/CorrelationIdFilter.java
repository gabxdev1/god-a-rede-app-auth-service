package br.com.gabxdev.infra.adapter.in.controller.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_KEY= "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String correlationId = extractOrGenerateCorrelationId(request);

        try {
            MDC.put(CORRELATION_ID_KEY, correlationId);
            org.jboss.logging.MDC.put(CORRELATION_ID_KEY, correlationId);

            request.setAttribute(CORRELATION_ID_KEY, correlationId);

            response.setHeader(CORRELATION_ID_KEY, correlationId);

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CORRELATION_ID_KEY);
            org.jboss.logging.MDC.remove(CORRELATION_ID_KEY);
        }
    }

    private String extractOrGenerateCorrelationId(HttpServletRequest request) {
        String headerValue = request.getHeader(CORRELATION_ID_KEY);
        if (headerValue != null && !headerValue.isBlank()) {
            return headerValue;
        }
        return UUID.randomUUID().toString();
    }
}
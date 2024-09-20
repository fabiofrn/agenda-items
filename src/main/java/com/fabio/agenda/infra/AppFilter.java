package com.fabio.agenda.infra;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String endpoint = httpRequest.getRequestURI();

        filterChain.doFilter(servletRequest, servletResponse);

        int statusCode = httpResponse.getStatus();

        LOGGER.info("Endpoint chamado: {} | Código de retorno: {}", endpoint, statusCode);
    }
}

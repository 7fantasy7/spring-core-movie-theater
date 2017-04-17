package ua.epam.spring.hometask.config.initializer;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    // This initializer creates org.springframework.web.filter.DelegatingFilterProxy to
    // implement springSecurityFilterChain
}

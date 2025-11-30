package com.fipe.infrastructure.config;

import com.fipe.domain.enums.Role;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.CreateUserUseCase;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SeedConfig {

    private static final Logger log = LoggerFactory.getLogger(SeedConfig.class);

    @Inject
    CreateUserUseCase createUserUseCase;

    @Inject
    GetUserUseCase getUserUseCase;

    @PostConstruct
    void seed() {
        for (Role role : Role.values()) {
            String username = role.name().toLowerCase();
            try {
                User existing = getUserUseCase.getByUsername(username);
                if (existing != null) {
                    log.info("User '{}' for role {} already exists, skipping.", username, role);
                    continue;
                }
            } catch (Exception e) {
                log.info("No existing user '{}' found, will create. Reason: {}", username, e.getMessage());
            }
            try {
                createUserUseCase.execute(
                    new CreateUserRequest(
                        username,
                        username + "@fipe.com",
                        "123456",
                        role.getName()
                    )
                );
                log.info("Seeded user '{}' with role {}", username, role);
            } catch (Exception e) {
                log.error("Failed to create seeded user '{}' for role {}: {}", username, role, e.getMessage(), e);
            }
        }
    }
}

package com.fipe.infrastructure.config;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "FIPE Gateway API",
                version = "1.0.0",
                description = "API for managing FIPE vehicle data initial load",
                contact = @Contact(name = "FIPE Team")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local development server")
        }
)
public class OpenApiConfig extends Application {
}

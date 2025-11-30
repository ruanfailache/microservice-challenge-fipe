package com.fipe.infrastructure.adapter.in.rest.service;

import com.fipe.infrastructure.adapter.out.rest.client.UserAuthClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class UserAuthService {
    
    @Inject
    @RestClient
    UserAuthClient userAuthClient;
    
    public UserAuthClient getUserAuthClient() {
        return userAuthClient;
    }
}


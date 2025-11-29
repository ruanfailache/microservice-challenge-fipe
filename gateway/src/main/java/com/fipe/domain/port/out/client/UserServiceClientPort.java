package com.fipe.domain.port.out.client;

import com.fipe.domain.enums.Role;
import com.fipe.infrastructure.adapter.out.rest.response.UserServiceResponse;

public interface UserServiceClientPort {
    
    Role validateCredentials(String username, String password);
    
    UserServiceResponse getUserByUsername(String username);
}

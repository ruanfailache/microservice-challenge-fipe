package com.fipe.domain.port.out.client;

import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;

public interface UserClientPort {
    UserResponse getCurrentUser(String authorization);
}

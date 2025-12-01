package com.fipe.domain.port.out.client;

import com.fipe.infrastructure.adapter.out.rest.dto.response.user.UserOutResponse;

public interface UserClientPort {
    UserOutResponse getCurrentUser(String authorization);
}

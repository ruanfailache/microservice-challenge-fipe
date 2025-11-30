package com.fipe.infrastructure.adapter.in.rest.mapper;

import com.fipe.domain.model.User;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserResponseMapper {
    
    @Mapping(target = "role", expression = "java(user.getRole() != null ? user.getRole().getName() : null)")
    UserResponse toResponse(User user);
}
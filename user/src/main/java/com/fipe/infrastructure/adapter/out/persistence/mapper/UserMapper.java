package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.User;
import com.fipe.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    UserEntity toEntityOnCreate(User user);
    
    UserEntity toEntity(User user);

    User toDomain(UserEntity entity);
}
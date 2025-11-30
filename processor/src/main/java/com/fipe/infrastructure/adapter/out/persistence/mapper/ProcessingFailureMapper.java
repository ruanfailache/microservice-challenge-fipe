package com.fipe.infrastructure.adapter.out.persistence.mapper;

import com.fipe.domain.model.ProcessingFailure;
import com.fipe.infrastructure.adapter.out.persistence.entity.ProcessingFailureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface ProcessingFailureMapper {

    @Mapping(target = "status", expression = "java(domain.getStatus() != null ? domain.getStatus().name() : null)")
    ProcessingFailureEntity toEntity(ProcessingFailure domain);

    @Mapping(target = "status", expression = "java(entity.getStatus() != null ? com.fipe.domain.enums.FailureStatus.valueOf(entity.getStatus()) : null)")
    ProcessingFailure toDomain(ProcessingFailureEntity entity);
}

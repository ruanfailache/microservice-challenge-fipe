package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInResponse {
    private Long id;
    private String brandCode;
    private String brandName;
    private String code;
    private String model;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.fipe.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Vehicle {
    @Setter
    private Long id;
    private final String brandCode;
    private final String brandName;
    private final String code;
    private String model;
    private String observations;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void updateModel(String model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.updatedAt = LocalDateTime.now();
    }
    
    public void updateObservations(String observations) {
        this.observations = observations;
        this.updatedAt = LocalDateTime.now();
    }

}

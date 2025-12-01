package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitialLoadInResponse {
    private String message;
    private Integer brandsProcessed;
}

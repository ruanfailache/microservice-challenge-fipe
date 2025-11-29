package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CleanupResponse {
    
    private String message;
    private long deletedCount;
}

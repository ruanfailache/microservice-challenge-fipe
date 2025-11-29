package com.fipe.infrastructure.adapter.in.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FailureStatisticsResponse {
    
    private long pendingRetry;
    private long retryExhausted;
    private long manualReview;
    private long resolved;
    private long total;
}

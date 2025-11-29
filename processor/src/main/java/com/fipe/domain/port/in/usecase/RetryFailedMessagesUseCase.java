package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.RetryResult;

public interface RetryFailedMessagesUseCase {
    
    RetryResult retryEligibleFailures();
}

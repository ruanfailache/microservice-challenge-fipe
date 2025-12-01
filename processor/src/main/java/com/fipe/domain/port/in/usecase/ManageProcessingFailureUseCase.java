package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.FailureStatistics;
import com.fipe.domain.model.ProcessingFailure;

import java.util.List;

public interface ManageProcessingFailureUseCase {

    ProcessingFailure markForRetry(Long failureId);
    
    ProcessingFailure markAsResolved(Long failureId);
    
    List<ProcessingFailure> findByStatus(String status);
    
    ProcessingFailure findById(Long id);
    
    List<ProcessingFailure> findByBrandCode(String brandCode);
    
    FailureStatistics getStatistics();
    
    long cleanupResolvedFailures(int daysOld);
}

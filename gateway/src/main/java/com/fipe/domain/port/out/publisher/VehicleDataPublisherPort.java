package com.fipe.domain.port.out.publisher;

import com.fipe.domain.model.Brand;

public interface VehicleDataPublisherPort {
    
    void publishBrandForProcessing(Brand brand);
}

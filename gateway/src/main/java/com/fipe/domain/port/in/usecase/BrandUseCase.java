package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;

import java.util.List;

public interface BrandUseCase {
    List<Brand> getAllBrands(String authorization);

    Brand getBrandByCode(String authorization, String code);
}

package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandUseCase {
    List<Brand> getAllBrands(String authorization);

    Optional<Brand> getBrandByCode(String authorization, String code);
}

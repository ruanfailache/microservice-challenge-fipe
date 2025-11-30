package com.fipe.domain.port.in.usecase;

import com.fipe.domain.model.Brand;
import lombok.NonNull;

import java.util.List;

public interface BrandQueryUseCase {
    
    List<Brand> getAllBrands();
    
    Brand getBrandByCode(String code);

    boolean existsBrandByCode(String code);
}

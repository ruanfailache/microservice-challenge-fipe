package com.fipe.domain.port.out.client;

import com.fipe.domain.model.Brand;

import java.util.List;

public interface FipeClientPort {
    List<Brand> fetchAllBrands();
}

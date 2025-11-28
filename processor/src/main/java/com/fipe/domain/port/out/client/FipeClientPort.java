package com.fipe.domain.port.out.client;

import com.fipe.domain.model.Model;

import java.util.List;

public interface FipeClientPort {
    
    List<Model> fetchModelsByBrand(String brandCode);
}

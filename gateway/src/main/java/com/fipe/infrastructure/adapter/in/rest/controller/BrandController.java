package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.Brand;
import com.fipe.domain.port.in.usecase.BrandQueryUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.BrandMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.BrandApi;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/brands")
@Produces(MediaType.APPLICATION_JSON)
public class BrandController implements BrandApi {
    
    @Inject
    BrandQueryUseCase brandQueryUseCase;
    
    @GET
    @PermitAll
    @Override
    public Response getAllBrands() {
        List<Brand> brands = brandQueryUseCase.getAllBrands();
        List<BrandResponse> dtos = brands.stream()
                .map(BrandMapper::toDTO)
                .collect(Collectors.toList());
        
        return Response.ok(dtos).build();
    }
    
    @GET
    @Path("/{code}")
    @PermitAll
    @Override
    public Response getBrandByCode(@PathParam("code") String code) {
        Brand brand = brandQueryUseCase.getBrandByCode(code);
        BrandResponse dto = BrandMapper.toDTO(brand);
        return Response.ok(dto).build();
    }
}

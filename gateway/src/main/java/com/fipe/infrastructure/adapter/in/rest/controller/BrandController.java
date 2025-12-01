package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.Vehicle;
import com.fipe.domain.port.in.usecase.BrandUseCase;
import com.fipe.domain.port.in.usecase.VehicleUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.VehicleUpdateRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.BrandInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.VehicleInResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.BrandMapper;
import com.fipe.infrastructure.adapter.in.rest.mapper.VehicleMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.BrandApi;
import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.security.annotation.RequiresRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/brands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BrandController implements BrandApi {
    
    @Inject
    BrandUseCase brandUseCase;

    @Inject
    VehicleUseCase vehicleUseCase;
    
    @Inject
    BrandMapper brandMapper;

    @Inject
    VehicleMapper vehicleMapper;
    
    @GET
    public Response getAllBrands(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
        List<Brand> brands = brandUseCase.getAllBrands(authorization);
        List<BrandInResponse> response = brandMapper.toDTO(brands);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/{code}")
    public Response getBrandByCode(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("code") String code
    ) {
        Brand brand = brandUseCase.getBrandByCode(authorization, code).orElse(null);
        if (brand == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        BrandInResponse response = brandMapper.toDTO(brand);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{brandCode}/vehicle")
    public Response getVehiclesByBrand(
            @PathParam("brandCode") String brandCode,
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        List<Vehicle> vehicles = vehicleUseCase.getVehiclesByBrandCode(authorization, brandCode);
        List<VehicleInResponse> response = vehicleMapper.toDTO(vehicles);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{brandCode}/vehicle")
    @RequiresRole({"USER", "ADMIN"})
    public Response updateVehicle(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("brandCode") String brandCode, VehicleUpdateRequest request
    ) {
        Vehicle updated = vehicleUseCase.updateVehicle(
                authorization,
                brandCode,
                new ProcessorUpdateVehicleOutRequest(request.getModel(), request.getObservations())
        );
        VehicleInResponse response = vehicleMapper.toDTO(updated);
        return Response.ok(response).build();
    }
}

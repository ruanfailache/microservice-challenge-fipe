package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.Brand;
import com.fipe.domain.model.VehicleData;
import com.fipe.domain.port.in.usecase.ManageVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorVehicleOutResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.ProcessorRestMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.VehicleDataApi;
import com.fipe.infrastructure.security.annotation.RequiresRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/api/v1/brand")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleDataController implements VehicleDataApi {

    @Inject
    ManageVehicleDataUseCase manageVehicleDataUseCase;

    @Inject
    ProcessorRestMapper processorRestMapper;

    @GET
    public Response getAllBrands() {
        List<Brand> brands = manageVehicleDataUseCase.getAllBrands();
        List<ProcessorBrandOutResponse> response = processorRestMapper.toBrandResponse(brands);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{brandCode}")
    public Response getBrandByCode(@PathParam("brandCode") String brandCode) {
        Optional<VehicleData> vehicleData = manageVehicleDataUseCase.getBrandByCode(brandCode);
        if (vehicleData.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        ProcessorBrandOutResponse response = processorRestMapper.toBrandResponse(vehicleData.get());
        return Response.ok(response).build();
    }

    @GET
    @Path("/{brandCode}/vehicle")
    public Response getVehiclesByBrand(@PathParam("brandCode") String brandCode) {
        List<VehicleData> vehicles = manageVehicleDataUseCase.getVehiclesByBrand(brandCode);
        List<ProcessorVehicleOutResponse> response = processorRestMapper.toVehicleResponseList(vehicles);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{brandCode}/vehicle")
    @RequiresRole({"ADMIN"})
    public Response updateVehicle(@PathParam("brandCode") String brandCode, ProcessorUpdateVehicleOutRequest request) {
        Optional<VehicleData> vehicleData = manageVehicleDataUseCase.getVehicleByBrandAndCode(brandCode, request.code());
        if (vehicleData.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        processorRestMapper.updateVehicleDataFromRequest(vehicleData.get(), request);
        VehicleData updated = manageVehicleDataUseCase.updateVehicle(vehicleData.get());
        ProcessorVehicleOutResponse response = processorRestMapper.toVehicleResponse(updated);
        return Response.ok(response).build();
    }
}

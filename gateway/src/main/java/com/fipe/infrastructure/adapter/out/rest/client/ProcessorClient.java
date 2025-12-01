package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.out.rest.dto.request.processor.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.out.rest.dto.response.processor.ProcessorVehicleOutResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "processor-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ProcessorClient {

    @GET
    @Path("/api/v1/brand")
    List<ProcessorBrandOutResponse> getAllBrands(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization
    );

    @GET
    @Path("/api/v1/brand/{brand}")
    ProcessorBrandOutResponse getBrandByCode(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("brand") String brand
    );

    @GET
    @Path("/api/v1/brand/{brand}/vehicle")
    List<ProcessorVehicleOutResponse> getVehiclesByBrand(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("brand") String brand
    );

    @PUT
    @Path("/api/v1/brand/{brand}/vehicle")
    ProcessorVehicleOutResponse updateVehicle(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("brand") String brand,
            ProcessorUpdateVehicleOutRequest vehicleRequest
    );
}

package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.model.FailureStatistics;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.in.usecase.ManageProcessingFailureUseCase;
import com.fipe.domain.port.in.usecase.ManageVehicleDataUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.response.CleanupResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.FailureStatisticsResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessingFailureResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.ProcessingFailureRestMapper;
import com.fipe.infrastructure.adapter.in.rest.openapi.FailureManagementApi;
import com.fipe.infrastructure.security.annotation.RequiresRole;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/failures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiresRole({"USER", "ADMIN"})
public class FailureManagementController implements FailureManagementApi {
    
    @Inject
    ManageProcessingFailureUseCase manageProcessingFailureUseCase;

    @GET
    @Path("/status/{status}")
    @Override
    public Response getByStatus(@PathParam("status") String status) {
        List<ProcessingFailure> failures = manageProcessingFailureUseCase.findByStatus(status);
        List<ProcessingFailureResponse> response = ProcessingFailureRestMapper.toResponseList(failures);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/{id}")
    @Override
    public Response getById(@PathParam("id") Long id) {
        ProcessingFailure failure = manageProcessingFailureUseCase.findById(id);
        ProcessingFailureResponse response = ProcessingFailureRestMapper.toResponse(failure);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/brand/{brandCode}")
    @Override
    public Response getByBrand(@PathParam("brandCode") String brandCode) {
        List<ProcessingFailure> failures = manageProcessingFailureUseCase.findByBrandCode(brandCode);
        List<ProcessingFailureResponse> response = ProcessingFailureRestMapper.toResponseList(failures);
        return Response.ok(response).build();
    }
    
    @PUT
    @Path("/{id}/retry")
    @RequiresRole({"ADMIN"})
    @Override
    public Response markForRetry(@PathParam("id") Long id) {
        ProcessingFailure failure = manageProcessingFailureUseCase.markForRetry(id);
        ProcessingFailureResponse response = ProcessingFailureRestMapper.toResponse(failure);
        return Response.ok(response).build();
    }
    
    @PUT
    @Path("/{id}/resolve")
    @RequiresRole({"ADMIN"})
    @Override
    public Response markAsResolved(@PathParam("id") Long id) {
        ProcessingFailure failure = manageProcessingFailureUseCase.markAsResolved(id);
        ProcessingFailureResponse response = ProcessingFailureRestMapper.toResponse(failure);
        return Response.ok(response).build();
    }
    
    @GET
    @Path("/statistics")
    @Override
    public Response getStatistics() {
        FailureStatistics stats = manageProcessingFailureUseCase.getStatistics();
        FailureStatisticsResponse response = ProcessingFailureRestMapper.toResponse(stats);
        return Response.ok(response).build();
    }
    
    @DELETE
    @Path("/cleanup")
    @RequiresRole({"ADMIN"})
    @Override
    public Response cleanup(@QueryParam("days") @DefaultValue("30") int days) {
        long deleted = manageProcessingFailureUseCase.cleanupResolvedFailures(days);
        CleanupResponse response = new CleanupResponse(
                "Successfully deleted old resolved failures",
                deleted
        );
        return Response.ok(response).build();
    }
}

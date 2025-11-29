package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.exception.ResourceNotFoundException;
import com.fipe.domain.model.ProcessingFailure;
import com.fipe.domain.port.in.usecase.ManageProcessingFailureUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/failures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FailureManagementController {
    
    @Inject
    ManageProcessingFailureUseCase useCase;
    
    @GET
    @Path("/status/{status}")
    public Response getByStatus(@PathParam("status") String status) {
        try {
            List<ProcessingFailure> failures = useCase.findByStatus(status);
            return Response.ok(failures).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid status. Valid values: PENDING_RETRY, RETRY_EXHAUSTED, MANUAL_REVIEW_REQUIRED, RESOLVED")
                    .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            ProcessingFailure failure = useCase.findById(id);
            return Response.ok(failure).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/brand/{brandCode}")
    public Response getByBrand(@PathParam("brandCode") String brandCode) {
        List<ProcessingFailure> failures = useCase.findByBrandCode(brandCode);
        return Response.ok(failures).build();
    }
    
    @PUT
    @Path("/{id}/retry")
    public Response markForRetry(@PathParam("id") Long id) {
        try {
            ProcessingFailure failure = useCase.markForRetry(id);
            return Response.ok(failure).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("/{id}/resolve")
    public Response markAsResolved(@PathParam("id") Long id) {
        try {
            ProcessingFailure failure = useCase.markAsResolved(id);
            return Response.ok(failure).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/statistics")
    public Response getStatistics() {
        ManageProcessingFailureUseCase.FailureStatistics stats = useCase.getStatistics();
        return Response.ok(stats).build();
    }
    
    @DELETE
    @Path("/cleanup")
    public Response cleanup(@QueryParam("days") @DefaultValue("30") int days) {
        long deleted = useCase.cleanupResolvedFailures(days);
        return Response.ok("Deleted " + deleted + " resolved failures").build();
    }
}

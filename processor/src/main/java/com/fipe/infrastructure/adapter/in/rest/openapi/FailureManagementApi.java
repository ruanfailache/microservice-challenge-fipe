package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.response.CleanupResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.FailureStatisticsResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessingFailureResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Failure Management", description = "Operations for managing processing failures")
public interface FailureManagementApi {
    
    @Operation(summary = "Get failures by status", 
               description = "Retrieve all processing failures filtered by status")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "List of failures",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessingFailureResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid status value",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getByStatus(
            @Parameter(description = "Failure status (PENDING_RETRY, RETRY_EXHAUSTED, MANUAL_REVIEW_REQUIRED, RESOLVED)", 
                       required = true) String status);
    
    @Operation(summary = "Get failure by ID", 
               description = "Retrieve a specific processing failure by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Failure found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessingFailureResponse.class))),
            @APIResponse(responseCode = "404", description = "Failure not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getById(
            @Parameter(description = "Failure ID", required = true) Long id);
    
    @Operation(summary = "Get failures by brand", 
               description = "Retrieve all processing failures for a specific brand")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "List of failures",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessingFailureResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getByBrand(
            @Parameter(description = "Brand code", required = true) String brandCode);
    
    @Operation(summary = "Mark failure for retry", 
               description = "Mark a failure to be retried in the next scheduled retry job")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Failure marked for retry",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessingFailureResponse.class))),
            @APIResponse(responseCode = "404", description = "Failure not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response markForRetry(
            @Parameter(description = "Failure ID", required = true) Long id);
    
    @Operation(summary = "Mark failure as resolved", 
               description = "Mark a failure as manually resolved")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Failure marked as resolved",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessingFailureResponse.class))),
            @APIResponse(responseCode = "404", description = "Failure not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response markAsResolved(
            @Parameter(description = "Failure ID", required = true) Long id);
    
    @Operation(summary = "Get failure statistics", 
               description = "Retrieve statistics about processing failures grouped by status")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Failure statistics",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = FailureStatisticsResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response getStatistics();
    
    @Operation(summary = "Cleanup old resolved failures", 
               description = "Delete resolved failures older than specified days")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Cleanup completed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = CleanupResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response cleanup(
            @Parameter(description = "Delete failures older than this many days", required = false) int days);
}

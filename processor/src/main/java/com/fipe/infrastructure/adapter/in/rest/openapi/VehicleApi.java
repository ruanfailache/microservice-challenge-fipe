package com.fipe.infrastructure.adapter.in.rest.openapi;

import com.fipe.infrastructure.adapter.in.rest.dto.request.ProcessorUpdateVehicleOutRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorBrandOutResponse;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ProcessorVehicleOutResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Vehicle", description = "Operations for managing processed vehicle data")
public interface VehicleApi {

    @Operation(summary = "Update vehicle",
               description = "Update a vehicle's information")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Vehicle updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ProcessorVehicleOutResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "404", description = "Vehicle not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorInResponse.class)))
    })
    Response updateVehicle(
            @Parameter(description = "Vehicle ID", required = true) Long vehicleId,
            @RequestBody(description = "Vehicle update data",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                schema = @Schema(implementation = ProcessorUpdateVehicleOutRequest.class)))
            ProcessorUpdateVehicleOutRequest request);
}

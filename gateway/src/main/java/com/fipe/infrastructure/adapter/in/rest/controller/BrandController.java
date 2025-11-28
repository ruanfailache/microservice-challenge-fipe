package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.application.service.BrandService;
import com.fipe.domain.model.Brand;
import com.fipe.infrastructure.adapter.in.rest.dto.BrandDTO;
import com.fipe.infrastructure.adapter.in.rest.mapper.BrandDTOMapper;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/v1/brands")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Brands", description = "Brand management operations")
public class BrandController {
    
    @Inject
    BrandService brandService;
    
    @GET
    @PermitAll
    @Operation(summary = "Get all brands", description = "Retrieves all vehicle brands from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brands retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandDTO.class))),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        List<BrandDTO> dtos = brands.stream()
                .map(BrandDTOMapper::toDTO)
                .collect(Collectors.toList());
        
        return Response.ok(dtos).build();
    }
    
    @GET
    @Path("/{code}")
    @PermitAll
    @Operation(summary = "Get brand by code", description = "Retrieves a specific brand by its code")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Brand retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BrandDTO.class))),
            @APIResponse(responseCode = "404", description = "Brand not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getBrandByCode(@PathParam("code") String code) {
        Brand brand = brandService.getBrandByCode(code);
        BrandDTO dto = BrandDTOMapper.toDTO(brand);
        return Response.ok(dto).build();
    }
}

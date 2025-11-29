package com.fipe.infrastructure.adapter.out.client.rest;

import com.fipe.infrastructure.adapter.out.rest.response.FipeBrandResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/carros/marcas")
@RegisterRestClient(configKey = "fipe-api")
public interface FipeRestClient {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<FipeBrandResponse> getBrands();
}

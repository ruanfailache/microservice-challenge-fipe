package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.out.rest.dto.response.fipe.FipeBrandOutResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/carros/marcas")
@RegisterRestClient(configKey = "fipe-api")
public interface FipeClient {
    
    @GET
    List<FipeBrandOutResponse> getBrands();
}


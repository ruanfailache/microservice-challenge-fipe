package com.fipe.infrastructure.adapter.out.rest.client;

import com.fipe.infrastructure.adapter.out.rest.response.FipeModelsWrapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/carros/marcas")
@RegisterRestClient(configKey = "fipe-api")
public interface FipeRestClient {
    
    @GET
    @Path("/{brandId}/modelos")
    @Produces(MediaType.APPLICATION_JSON)
    FipeModelsWrapper getModels(@PathParam("brandId") String brandId);
}

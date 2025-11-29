package com.fipe.infrastructure.adapter.in.rest.exception;

import com.fipe.domain.exception.ResourceNotFoundException;
import com.fipe.domain.exception.VehicleDataProcessingException;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    
    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);
    
    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Exception caught by global handler", exception);
        
        if (exception instanceof ResourceNotFoundException foundException) {
            return handleResourceNotFoundException(foundException);
        }
        
        if (exception instanceof VehicleDataProcessingException processingException) {
            return handleVehicleDataProcessingException(processingException);
        }
        
        if (exception instanceof IllegalArgumentException argumentException) {
            return handleIllegalArgumentException(argumentException);
        }
        
        return handleGenericException(exception);
    }
    
    private Response handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
    
    private Response handleVehicleDataProcessingException(VehicleDataProcessingException exception) {
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
    
    private Response handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponse error = new ErrorResponse(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
    
    private Response handleGenericException(Exception exception) {
        String message = "An unexpected error occurred";
        ErrorResponse error = new ErrorResponse(message, exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}

package com.fipe.infrastructure.adapter.in.rest.exception;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.exception.ValidationException;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

/**
 * Global exception handler for REST endpoints
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {
    
    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);
    
    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Exception caught", exception);
        
        if (exception instanceof NotFoundException foundException) {
            return handleNotFoundException(foundException);
        }
        
        if (exception instanceof ValidationException validationException) {
            return handleValidationException(validationException);
        }
        
        if (exception instanceof AuthenticationException authenticationException) {
            return handleAuthenticationException(authenticationException);
        }
        
        return handleGenericException(exception);
    }
    
    private Response handleNotFoundException(NotFoundException exception) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
    
    private Response handleValidationException(ValidationException exception) {
        ErrorResponse error = new ErrorResponse("VALIDATION_ERROR", exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
    
    private Response handleAuthenticationException(AuthenticationException exception) {
        ErrorResponse error = new ErrorResponse("AUTHENTICATION_ERROR", exception.getMessage());
        return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
    }
    
    private Response handleGenericException(Exception exception) {
        ErrorResponse error = new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
    }
}

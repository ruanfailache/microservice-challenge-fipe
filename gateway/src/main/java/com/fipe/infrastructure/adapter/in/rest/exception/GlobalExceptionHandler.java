package com.fipe.infrastructure.adapter.in.rest.exception;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.exception.CacheException;
import com.fipe.domain.exception.MessagingException;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.exception.InitialLoadException;
import com.fipe.infrastructure.adapter.in.rest.dto.ErrorResponse;
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
        
        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof AuthenticationException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof ExternalServiceException) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof InitialLoadException) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(new ErrorResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof MessagingException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Messaging service error: " + exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof CacheException) {
            LOG.warn("Cache exception occurred, continuing without cache", exception);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Cache service error: " + exception.getMessage()))
                    .build();
        }
        
        // Default handler for unexpected exceptions
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("An unexpected error occurred"))
                .build();
    }
}

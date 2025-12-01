package com.fipe.infrastructure.adapter.in.rest.exception;

import com.fipe.domain.exception.AuthenticationException;
import com.fipe.domain.exception.CacheException;
import com.fipe.domain.exception.MessagingException;
import com.fipe.domain.exception.NotFoundException;
import com.fipe.domain.exception.ExternalServiceException;
import com.fipe.domain.exception.InitialLoadException;
import com.fipe.domain.exception.ValidationException;
import com.fipe.infrastructure.adapter.in.rest.dto.response.ErrorInResponse;
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
        
        if (exception instanceof ValidationException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorInResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorInResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof AuthenticationException) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorInResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof ExternalServiceException) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity(new ErrorInResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof InitialLoadException) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(new ErrorInResponse(exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof MessagingException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorInResponse("Messaging service error: " + exception.getMessage()))
                    .build();
        }
        
        if (exception instanceof CacheException) {
            LOG.warn("Cache exception occurred, continuing without cache", exception);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorInResponse("Cache service error: " + exception.getMessage()))
                    .build();
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorInResponse("An unexpected error occurred"))
                .build();
    }
}

package com.fipe.infrastructure.adapter.in.rest.controller;

import com.fipe.domain.enums.Role;
import com.fipe.domain.model.User;
import com.fipe.domain.port.in.usecase.ChangePasswordUseCase;
import com.fipe.domain.port.in.usecase.CreateUserUseCase;
import com.fipe.domain.port.in.usecase.DeleteUserUseCase;
import com.fipe.domain.port.in.usecase.GetUserUseCase;
import com.fipe.domain.port.in.usecase.UpdateUserUseCase;
import com.fipe.infrastructure.adapter.in.rest.dto.request.ChangePasswordRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.request.CreateUserRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.request.UpdateUserRequest;
import com.fipe.infrastructure.adapter.in.rest.dto.response.UserResponse;
import com.fipe.infrastructure.adapter.in.rest.mapper.UserResponseMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for User CRUD operations
 */
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "User management operations")
public class UserController {
    
    @Inject
    CreateUserUseCase createUserUseCase;
    
    @Inject
    UpdateUserUseCase updateUserUseCase;
    
    @Inject
    GetUserUseCase getUserUseCase;
    
    @Inject
    DeleteUserUseCase deleteUserUseCase;
    
    @Inject
    ChangePasswordUseCase changePasswordUseCase;
    
    @Inject
    UserResponseMapper userResponseMapper;
    
    @POST
    @RolesAllowed({"ADMIN"})
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details. Requires ADMIN role.")
    public Response createUser(CreateUserRequest request) {
        Role role = request.role() != null ? Role.fromString(request.role()) : Role.USER;
        
        User user = createUserUseCase.execute(
            new CreateUserUseCase.CreateUserRequest(
                request.username(),
                request.email(),
                request.password(),
                role
            )
        );
        
        return Response.status(Response.Status.CREATED)
            .entity(userResponseMapper.toResponse(user))
            .build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    @Operation(summary = "Update a user", description = "Updates an existing user. Requires ADMIN role.")
    public Response updateUser(@PathParam("id") Long id, UpdateUserRequest request) {
        Role role = request.role() != null ? Role.fromString(request.role()) : null;
        
        User user = updateUserUseCase.execute(
            id,
            new UpdateUserUseCase.UpdateUserRequest(
                request.email(),
                role,
                request.active()
            )
        );
        
        return Response.ok(userResponseMapper.toResponse(user)).build();
    }
    
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID. Requires ADMIN or MANAGER role.")
    public Response getUserById(@PathParam("id") Long id) {
        User user = getUserUseCase.getById(id);
        return Response.ok(userResponseMapper.toResponse(user)).build();
    }
    
    @GET
    @Path("/username/{username}")
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username. Requires ADMIN or MANAGER role.")
    public Response getUserByUsername(@PathParam("username") String username) {
        User user = getUserUseCase.getByUsername(username);
        return Response.ok(userResponseMapper.toResponse(user)).build();
    }
    
    @GET
    @RolesAllowed({"ADMIN", "MANAGER"})
    @Operation(summary = "Get all users", description = "Retrieves all users. Requires ADMIN or MANAGER role.")
    public Response getAllUsers(@QueryParam("activeOnly") @DefaultValue("false") boolean activeOnly) {
        List<User> users = activeOnly ? getUserUseCase.getAllActive() : getUserUseCase.getAll();
        List<UserResponse> response = users.stream()
            .map(userResponseMapper::toResponse)
            .collect(Collectors.toList());
        
        return Response.ok(response).build();
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    @Operation(summary = "Delete a user", description = "Deletes a user by their ID. Requires ADMIN role.")
    public Response deleteUser(@PathParam("id") Long id) {
        deleteUserUseCase.execute(id);
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/{id}/password")
    @RolesAllowed({"ADMIN", "MANAGER", "USER"})
    @Operation(summary = "Change user password", description = "Changes the password for a user. Users can change their own password.")
    public Response changePassword(@PathParam("id") Long id, ChangePasswordRequest request) {
        changePasswordUseCase.execute(
            id,
            new ChangePasswordUseCase.ChangePasswordRequest(
                request.currentPassword(),
                request.newPassword()
            )
        );
        
        return Response.noContent().build();
    }
}

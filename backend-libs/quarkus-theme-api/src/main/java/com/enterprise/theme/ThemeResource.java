package com.enterprise.theme;

import com.enterprise.theme.model.Theme;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;

@Path("/api/themes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Themes", description = "Theme management endpoints")
public class ThemeResource {

    @Inject
    ThemeService service;

    @GET
    @Operation(summary = "List all themes")
    public List<Theme> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/active")
    @Operation(summary = "Get currently active theme")
    public Theme getActive() {
        return service.findActive();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get theme by ID")
    public Theme getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Operation(summary = "Create a new theme")
    public Response create(Theme theme) {
        Theme created = service.create(theme);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing theme")
    public Theme update(@PathParam("id") Long id, Theme payload) {
        return service.update(id, payload);
    }

    @PUT
    @Path("/{id}/activate")
    @Operation(summary = "Set theme as active")
    public Theme activate(@PathParam("id") Long id) {
        return service.activate(id);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a theme")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}

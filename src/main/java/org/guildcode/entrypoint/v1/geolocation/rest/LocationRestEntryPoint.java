package org.guildcode.entrypoint.v1.geolocation.rest;


import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;
import org.guildcode.application.services.ServiceTag;
import org.guildcode.application.services.location.update.UpdateUserLocationService;
import org.guildcode.application.services.location.update.dto.LocationRequestDto;
import org.guildcode.application.services.location.update.dto.UserLocationRequestDto;
import org.guildcode.entrypoint.v1.geolocation.LocationEntryPoint;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Traced
@RequestScoped
@Tag(name = ServiceTag.LOCATION)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/location")
public class LocationRestEntryPoint implements LocationEntryPoint {

    @Inject
    UpdateUserLocationService updateUserLocationService;
    @Inject
    JsonWebToken jwt;

    @Override
    @POST
    @PermitAll
    @Operation(summary = "Update location from user logged")
    @RequestBody(content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = LocationRequestDto.class)))
    @APIResponse(responseCode = "200", description = "User location updated with success")
    @APIResponse(responseCode = "422", description = "It was not possible to update user location.")
    public Uni<Response> update(LocationRequestDto location) {

        var email = jwt.<String>getClaim("email");
        return updateUserLocationService.handle(new UserLocationRequestDto(email, location))
                .map(data -> Response
                        .status(data.getStatus().toNumber())
                        .build());
    }
}

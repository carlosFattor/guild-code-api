package org.guildcode.entrypoint.v1.tags.rest;

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
import org.guildcode.application.services.location.dto.LocationRequestDto;
import org.guildcode.application.services.user.tag.TagService;
import org.guildcode.application.services.user.tag.dto.TagRequestDto;
import org.guildcode.application.services.user.tag.dto.UserTagRequestDto;
import org.guildcode.entrypoint.v1.tags.UserTagsEntryPoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Traced
@RequestScoped
@Tag(name = ServiceTag.USER_TAGS)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/tags")
public class UserTagsRestEntryPoint implements UserTagsEntryPoint {

    @Inject
    JsonWebToken jwt;
    @Inject
    TagService tagService;

    @Override
    @PUT
    @Operation(summary = "Update location from user logged")
    @RequestBody(content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = TagRequestDto.class)))
    @APIResponse(responseCode = "200", description = "User tags updated with success")
    @APIResponse(responseCode = "422", description = "It was not possible to update user tag's.")
    public Uni<Response> updateUserTags(TagRequestDto tags) {

        var email = jwt.<String>getClaim("email");
        var userTags = new UserTagRequestDto(email, tags);

        return tagService.handle(userTags)
                .map(resp -> Response.status(resp.getStatus().toNumber())
                        .entity(resp.getResponse())
                        .build());
    }
}

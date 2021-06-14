package org.guildcode.entrypoint.v1.user.rest;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;
import org.guildcode.application.services.ServiceTag;
import org.guildcode.application.services.github.add.dto.AddGithubUserResponseDto;
import org.guildcode.application.services.user.info.UserInfoService;
import org.guildcode.application.services.user.info.dto.UserInfoRequestDto;
import org.guildcode.entrypoint.v1.user.UserInfoEntrypoint;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Traced
@RequestScoped
@Tag(name = ServiceTag.USER_INFO)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/api/v1/user")
public class UserInfoRestEntrypoint implements UserInfoEntrypoint {

    @Inject
    UserInfoService userInfoService;
    @Inject
    JsonWebToken jwt;

    @Override
    @GET
    @PermitAll
    @Operation(summary = "Get user info logged")
    @APIResponse(responseCode = "200", description = "", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AddGithubUserResponseDto.class)))
    public Uni<Response> getInfo() {
        var email = jwt.<String>getClaim("email");
        var reqInfo = new UserInfoRequestDto(email);
        return userInfoService.handle(reqInfo)
                .map(userInfo -> Response.status(userInfo.getStatus().toNumber())
                        .entity(userInfo.getResponse())
                        .build());
    }
}

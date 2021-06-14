package org.guildcode.entrypoint.v1.login.rest;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;
import org.guildcode.application.services.ServiceTag;
import org.guildcode.application.services.github.add.GithubUserService;
import org.guildcode.application.services.github.add.dto.AddGithubUserRequestDto;
import org.guildcode.application.services.github.add.dto.AddGithubUserResponseDto;
import org.guildcode.entrypoint.v1.login.GithubLoginUserEntrypoint;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@Traced
@RequestScoped
@Tag(name = ServiceTag.GITHUB_USER)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("")
public class GithubLoginUserRestEntryPoint implements GithubLoginUserEntrypoint {

    @ConfigProperty(name = "git.url_github_redirect")
    String url_github_redirect;
    @ConfigProperty(name = "git.client_id")
    String client_id;
    @Inject
    GithubUserService githubService;

    @Override
    @GET
    @Path("/api/v1/github")
    @Operation(summary = "Get user data")
    @APIResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AddGithubUserResponseDto.class)))
    @APIResponse(responseCode = "422", description = "Não foi possível encontrar o usuário no github.")
    public Uni<Response> add(@QueryParam("code") String code) {
        var gitTokenDto = new AddGithubUserRequestDto(code);
        return githubService.handle(gitTokenDto)
                .map(userTokens ->
                        Response.status(userTokens.getStatus().toNumber())
                                .entity(userTokens.getResponse())
                                .build()
                );
    }

    @Override
    @GET()
    @Path("/users/github/login/redirect")
    @Operation(summary = "User redirect")
    @APIResponse(responseCode = "302", description = "User redirect to github", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    public Uni<Response> redirect() {

        var url = new StringBuilder()
                .append(url_github_redirect)
                .append("?client_id=")
                .append(client_id).toString();

        return Uni.createFrom().item(Response
                .status(302)
                .header("location", url).build());
    }
}

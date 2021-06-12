package org.guildcode.infrastructure.data.rest.github.integration;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.guildcode.domain.user.GithubUser;
import org.guildcode.infrastructure.config.rest.client.exception.CustomException;
import org.guildcode.infrastructure.data.rest.github.dto.GithubResponse;
import org.guildcode.infrastructure.config.rest.client.filter.RestClientResponseFilter;
import org.guildcode.infrastructure.data.rest.shared.AdapterExceptionDto;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@RegisterRestClient(configKey = "github")
@RegisterProvider(RestClientResponseFilter.class)
public interface RestGithubIntegration {

    @POST
    @Path("/login/oauth/access_token")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CustomException(AdapterExceptionDto.class)
    Uni<GithubResponse> getGithubToken(
            @QueryParam("client_id") String client_id,
            @QueryParam("client_secret") String client_secret,
            @QueryParam("code") String token);
}

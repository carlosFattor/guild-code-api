package org.guildcode.infrastructure.data.rest.github.integration;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.guildcode.domain.user.GithubUser;
import org.guildcode.infrastructure.config.rest.client.exception.CustomException;
import org.guildcode.infrastructure.config.rest.client.filter.RestClientResponseFilter;
import org.guildcode.infrastructure.data.rest.shared.AdapterExceptionDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(configKey = "github-api")
@RegisterProvider(RestClientResponseFilter.class)
public interface ApiGithubResource {

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CustomException(AdapterExceptionDto.class)
    Uni<GithubUser> getGithubUserInfo(@HeaderParam("Authorization") String githubToken);
}

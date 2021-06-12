package org.guildcode.domain.user.services;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.guildcode.domain.user.GithubUser;
import org.guildcode.domain.user.integration.GithubResource;
import org.guildcode.infrastructure.data.rest.github.integration.ApiGithubResource;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GithubService {

    @ConfigProperty(name = "git.client_id")
    String clientId;

    @ConfigProperty(name = "git.client_secret")
    String clientSecret;

    @Inject
    GithubResource githubResource;

    @RestClient
    ApiGithubResource apiGithubResource;

    public Uni<GithubUser> getGithubUser(String githubToken) {
        return githubResource.getGithubUserToken(clientId, clientSecret, githubToken)
                .onItem().transformToUni(githubResource::getUserInfoFromGithub);
    }
}

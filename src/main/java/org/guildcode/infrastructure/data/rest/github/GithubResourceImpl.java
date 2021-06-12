package org.guildcode.infrastructure.data.rest.github;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.token.Token;
import org.guildcode.domain.user.integration.GithubResource;
import org.guildcode.infrastructure.data.exceptions.GithubUserEmailNotFound;
import org.guildcode.infrastructure.data.exceptions.IntegrationException;
import org.guildcode.infrastructure.data.exceptions.UserNotFoundException;
import org.guildcode.infrastructure.data.rest.github.dto.GithubResponse;
import org.guildcode.domain.user.GithubUser;
import org.guildcode.infrastructure.data.rest.github.integration.ApiGithubResource;
import org.guildcode.infrastructure.data.rest.github.integration.RestGithubIntegration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GithubResourceImpl implements GithubResource {

    @Inject
    @RestClient
    RestGithubIntegration restGithubIntegration;

    @Inject
    @RestClient
    ApiGithubResource apiGithubResource;

    @Override
    public Uni<Token> getGithubUserToken(String client_id, String client_secret, String token) {
        return restGithubIntegration.getGithubToken(client_id,client_secret, token)
                .onFailure(IntegrationException.class)
                .transform(notFound -> new ApplicationException((DomainException) notFound, StatusCode.NOT_FOUND))
                .onItem().transformToUni(validateToken::apply)
                .invoke(tokenDto -> log.info("Validated token = {}", tokenDto));
    }

    @Override
    public Uni<GithubUser> getUserInfoFromGithub(Token token) {
        return apiGithubResource.getGithubUserInfo(token.getToken())
                .onItem().transformToUni(githubUserValidate::apply)
                .onFailure(GithubUserEmailNotFound.class)
                .transform(notFound -> new ApplicationException((DomainException) notFound, StatusCode.NOT_FOUND));
    }

    Function<GithubResponse, Uni<Token>> validateToken = gitToken -> {
        if(gitToken.getError()!= null) {
            return Uni.createFrom().failure(new UserNotFoundException());
        }
        return Uni.createFrom().item(new Token(gitToken.getAccess_token(), gitToken.getToken_type()));
    };

    Function<GithubUser, Uni<GithubUser>> githubUserValidate = gitUser -> {
        if(StringUtils.isEmpty(gitUser.getEmail())) {
            return Uni.createFrom().failure(new GithubUserEmailNotFound());
        }
        return Uni.createFrom().item(gitUser);
    };
}

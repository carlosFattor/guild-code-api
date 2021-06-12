package org.guildcode.domain.user.integration;

import io.smallrye.mutiny.Uni;
import org.guildcode.domain.token.Token;
import org.guildcode.domain.user.GithubUser;

public interface GithubResource {

    Uni<Token> getGithubUserToken(
            String client_id,
            String client_secret,
            String token);

    Uni<GithubUser> getUserInfoFromGithub(Token token);
}

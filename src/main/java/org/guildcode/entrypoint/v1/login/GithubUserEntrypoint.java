package org.guildcode.entrypoint.v1.login;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.core.Response;

public interface GithubUserEntrypoint {

    Uni<Response> add(String code);

//    Uni<Response> refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    Uni<Response> redirect();
}

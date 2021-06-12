package org.guildcode.domain.token.service;

import io.smallrye.mutiny.Uni;
import org.guildcode.domain.token.Tokens;
import org.guildcode.domain.user.User;

public interface TokenService {

    Uni<Tokens> generateToken(User user);
}

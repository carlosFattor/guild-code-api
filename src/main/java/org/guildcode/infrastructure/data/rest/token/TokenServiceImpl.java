package org.guildcode.infrastructure.data.rest.token;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.mutiny.core.eventbus.EventBus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.action.TokenGenerator;
import org.guildcode.domain.token.Tokens;
import org.guildcode.domain.token.service.TokenService;
import org.guildcode.domain.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenServiceImpl implements TokenService {

    @Inject
    EventBus eventBus;

    public Uni<Tokens> generateToken(User user) {
        user.setId(null);
        return eventBus.<String>request(TokenGenerator.JWT_GENERATOR_CHANNEL, Json.encode(user))
                .onItem()
                .transform(data -> {
                    return Json.decodeValue(data.body(), Tokens.class);
                });
    }
}

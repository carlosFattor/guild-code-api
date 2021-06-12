package org.guildcode.entrypoint.v1.geolocation;

import io.quarkus.vertx.web.RoutingExchange;
import org.guildcode.application.services.location.update.dto.UserLocationRequestDto;

public interface LocationEntryPoint {

    void update(RoutingExchange re, UserLocationRequestDto req);
}

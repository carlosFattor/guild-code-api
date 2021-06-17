package org.guildcode.domain.location.repository;

import io.smallrye.mutiny.Uni;
import org.guildcode.domain.location.Location;

public interface LocationRepository {

    Uni<Long> updateLocationByEmail(String email, Location location);
}

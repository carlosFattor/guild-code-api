package org.guildcode.infrastructure.data.mongo.repository;

import io.smallrye.mutiny.Uni;
import org.guildcode.domain.location.Location;
import org.guildcode.domain.location.repository.LocationRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocationRepositoryImpl implements LocationRepository, MongoUserRepository {
    @Override
    public Uni<Long> updateLocationByEmail(String email, Location location) {
        var query = "{'email': ?1}";
        var update = "{$set: {'location': {'type': ?1, 'coordinates': [?2]}}}";

        return update(update, location.getType(), location.getCoordinates().toArray()).where(query, email);
    }
}

package org.guildcode.infrastructure.data.mongo.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.repository.UserRepository;
import org.guildcode.infrastructure.data.mongo.mapper.UserEntityMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, MongoUserRepository {

    private static final Double DEFAULT_VALUE = Double.parseDouble("3963.2");

    @Inject
    UserEntityMapper userEntityMapper;

    @Override
    public Uni<User> findByEmail(String email) {

        var query = "{email: ?1}";
        return find(query, email)
                .firstResult()
                .onItem()
                .transform(userEntityMapper::map);
    }

    @Override
    public Multi<User> findByLocation(final Double lng, final Double lat, final int zoom) {
        var zoomP = (Double) positions.get(zoom);
        var radius = zoomP / DEFAULT_VALUE;
        var query = "{location: { $geoWithin: { $centerSphere: [[?1, ?2], ?3] }}}";

        return find(query, lng, lat, radius)
                .stream()
                .map(userEntityMapper::map);
    }

    @Override
    public Uni<Void> update(User user) {
        var userEntity = userEntityMapper.map(user);
        return persistOrUpdate(userEntity);
    }

    @Override
    public Uni<Long> updateTags(String email, Collection<String> tags) {
        var query = "{'email': ?1}";
        var update = "{$set: {'tags': [?1]}}";

        return update(update, tags).where(query, email);
    }
}

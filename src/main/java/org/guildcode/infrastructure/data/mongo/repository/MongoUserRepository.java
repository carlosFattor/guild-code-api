package org.guildcode.infrastructure.data.mongo.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import org.guildcode.infrastructure.data.mongo.entity.UserEntity;

public interface MongoUserRepository extends ReactivePanacheMongoRepository<UserEntity> {
}

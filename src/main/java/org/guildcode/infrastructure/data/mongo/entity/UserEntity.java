package org.guildcode.infrastructure.data.mongo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.MongoEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.guildcode.domain.user.User;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MongoEntity(collection = "users")
public class UserEntity extends User {

    @BsonId
    @JsonProperty("_id")
    ObjectId id;
}

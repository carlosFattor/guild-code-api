package org.guildcode.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.guildcode.domain.enums.Role;

import java.util.Collection;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    ObjectId id;
    String email;
    String password;
    String name;
    Collection<String> tags;
    Location location;
    Collection<Role> roles;
    GithubInfo gitInfo;
}

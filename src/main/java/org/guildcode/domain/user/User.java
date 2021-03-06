package org.guildcode.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.guildcode.domain.enums.Role;
import org.guildcode.domain.location.Location;

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

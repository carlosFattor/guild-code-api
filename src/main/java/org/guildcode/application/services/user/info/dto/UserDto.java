package org.guildcode.application.services.user.info.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.guildcode.domain.enums.Role;
import org.guildcode.domain.location.Location;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDto {

    String email;
    String name;
    Collection<String> tags;
    Location location;
    Collection<Role> roles;
    GithubInfoDto gitInfo;
}

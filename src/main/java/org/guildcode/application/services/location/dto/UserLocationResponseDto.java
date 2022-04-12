package org.guildcode.application.services.location.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.application.services.shared.dto.UserDto;
import org.guildcode.infrastructure.service.dto.Dto;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLocationResponseDto implements Dto {
    UserDto userDto;
}

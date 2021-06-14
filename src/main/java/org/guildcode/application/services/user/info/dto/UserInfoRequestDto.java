package org.guildcode.application.services.user.info.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.service.dto.Dto;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInfoRequestDto implements Dto {
    @NotNull(message = "Email info is necessary")
    String email;
}

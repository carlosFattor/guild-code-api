package org.guildcode.application.services.location.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.config.rest.client.exception.ExtendedEmailValidator;
import org.guildcode.infrastructure.service.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLocationRequestDto implements Dto {

    @ExtendedEmailValidator
    @NotNull(message = "the Email is necessary")
    String email;

    @Valid
    @NotNull(message = "the Location is necessary")
    LocationRequestDto location;
}

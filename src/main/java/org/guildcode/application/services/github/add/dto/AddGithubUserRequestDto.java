package org.guildcode.application.services.github.add.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.service.dto.Dto;

import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddGithubUserRequestDto implements Dto {

    @NotNull(message = "O token é obrigatório.")
    String token;
}

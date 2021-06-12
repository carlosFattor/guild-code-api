package org.guildcode.application.services.github.add.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.guildcode.domain.token.Tokens;
import org.guildcode.domain.user.BasicUser;
import org.guildcode.infrastructure.service.dto.Dto;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddGithubUserResponseDto  implements Dto {

    @JsonProperty("tokensInfo")
    @Schema(required = true, description = "Tokens de autenticação, refresh e tipo")
    Tokens tokenData;
}

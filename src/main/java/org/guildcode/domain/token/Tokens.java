package org.guildcode.domain.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tokens {

    @Schema(required = true, description = "Tipo do token.")
    String type;
    @Schema(required = true, description = "Token de identificação")
    String token;
    @Schema(required = true, description = "Refresh token.")
    String refreshToken;
}

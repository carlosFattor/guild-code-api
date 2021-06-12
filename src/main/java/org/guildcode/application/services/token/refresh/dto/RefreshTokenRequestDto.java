package org.guildcode.application.services.token.refresh.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.service.dto.Dto;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRequestDto implements Dto {
    String token;
}

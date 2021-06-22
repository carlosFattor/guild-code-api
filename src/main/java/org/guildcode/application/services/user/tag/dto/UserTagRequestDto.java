package org.guildcode.application.services.user.tag.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.service.dto.Dto;

@Data
@Builder
@AllArgsConstructor()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserTagRequestDto implements Dto {
    String Email;
    TagRequestDto tags;
}

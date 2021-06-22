package org.guildcode.application.services.user.tag.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.service.dto.Dto;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagRequestDto implements Dto {
    Collection<String> tags;
}

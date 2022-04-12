package org.guildcode.application.services.shared.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GithubInfoDto {

    String avatarUrl;
    String url;
    String bio;
    String blog;
    Integer publicRepos;
    String reposUrl;
}

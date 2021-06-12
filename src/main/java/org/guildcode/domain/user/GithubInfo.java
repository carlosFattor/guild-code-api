package org.guildcode.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GithubInfo {

    Integer id;
    String login;
    String name;
    String avatarUrl;
    String email;
    String url;
    String bio;
    String blog;
    Integer publicRepos;
    String reposUrl;
}

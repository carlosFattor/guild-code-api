package org.guildcode.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GithubUser {

    Integer id;
    String login;
    String name;
    String avatar_url;
    String email;
    String url;
    String bio;
    String blog;
    Integer public_repos;
    String repos_url;
}

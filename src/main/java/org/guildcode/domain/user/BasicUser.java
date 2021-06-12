package org.guildcode.domain.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.guildcode.domain.enums.Role;

import java.util.Collection;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BasicUser {

    String email;
    String avatarUrl;
    Collection<String> tags;
    String bio;
    String blog;
    Collection<Role> roles;

    public BasicUser(final User user) {
        this.email = user.getEmail();
        this.avatarUrl = user.getGitInfo().getAvatarUrl();
        this.tags = user.getTags();
        this.bio = user.getGitInfo().getBio();
        this.blog = user.getGitInfo().getBlog();
        this.roles = user.getRoles();
    }
}

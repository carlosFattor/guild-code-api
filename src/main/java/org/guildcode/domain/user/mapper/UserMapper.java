package org.guildcode.domain.user.mapper;

import org.guildcode.domain.user.GithubInfo;
import org.guildcode.domain.user.GithubUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserMapper {

    @Mapping(target = "avatarUrl", source = "avatar_url")
    @Mapping(target = "publicRepos", source = "public_repos")
    GithubInfo map(GithubUser user);
}

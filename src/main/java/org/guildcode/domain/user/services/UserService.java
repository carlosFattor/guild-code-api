package org.guildcode.domain.user.services;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.enums.Role;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.user.GithubUser;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.mapper.UserMapper;
import org.guildcode.domain.user.repository.UserRepository;
import org.guildcode.infrastructure.data.exceptions.UserNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    UserMapper userMapper;

    public Uni<User> findAndParse(GithubUser gitUser) {

        return userRepository.findByEmail(gitUser.getEmail())
                .onItem().ifNotNull().transform(user -> formatUserData(user, gitUser))
                .onItem().ifNull().continueWith(() -> formatUserData(new User(), gitUser));
    }

    private User formatUserData(User user, GithubUser gitUser) {
        user.setGitInfo(userMapper.map(gitUser));
        user.setEmail(gitUser.getEmail());
        user.setName(gitUser.getName());
        if (CollectionUtils.isEmpty(user.getRoles())) {
            user.setRoles(Collections.singletonList(Role.USER));
        }
        return user;
    }

    public Uni<User> updateUser(User user) {
        return userRepository.update(user)
                .onItem().transform((data) -> findByEmail(user.getEmail()))
                .flatMap(data -> data);
    }

    public Uni<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

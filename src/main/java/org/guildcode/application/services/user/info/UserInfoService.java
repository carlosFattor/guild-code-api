package org.guildcode.application.services.user.info;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.services.user.info.dto.GithubInfoDto;
import org.guildcode.application.services.user.info.dto.UserDto;
import org.guildcode.application.services.user.info.dto.UserInfoRequestDto;
import org.guildcode.application.services.user.info.dto.UserInfoResponseDto;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.services.UserService;
import org.guildcode.infrastructure.data.exceptions.UserNotFoundException;
import org.guildcode.infrastructure.service.ReactiveService;
import org.guildcode.infrastructure.service.result.ResponseResult;
import org.guildcode.infrastructure.service.result.ResponseStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.function.Function;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApplicationScoped
public class UserInfoService implements ReactiveService<UserInfoRequestDto> {

    @Inject
    Validator validator;
    @Inject
    UserService userService;

    @Override
    public Uni<ResponseResult> handle(UserInfoRequestDto request) {

        final var validationResults = validator.validate(request);

        if (!validationResults.isEmpty()) {
            return Uni.createFrom().failure(new ConstraintViolationException(validationResults));
        }

        return userService.findByEmail(request.getEmail())
                .onItem()
                .transform(toResponse)
                .onFailure(UserNotFoundException.class)
                .transform(notFound -> new ApplicationException((DomainException) notFound, StatusCode.NOT_FOUND));
    }

    Function<User, ResponseResult> toResponse = user -> {
        var githubInfoDto = GithubInfoDto.builder()
                .avatarUrl(user.getGitInfo().getAvatarUrl())
                .bio(user.getGitInfo().getBio())
                .blog(user.getGitInfo().getBlog())
                .publicRepos(user.getGitInfo().getPublicRepos())
                .reposUrl(user.getGitInfo().getReposUrl())
                .url(user.getGitInfo().getUrl())
                .build();
        var userDto = UserDto.builder()
                .gitInfo(githubInfoDto)
                .email(user.getEmail())
                .location(user.getLocation())
                .name(user.getName())
                .roles(user.getRoles())
                .tags(user.getTags())
                .build();
        return new ResponseResult(ResponseStatus.ACCEPTED, new UserInfoResponseDto(userDto));
    };
}

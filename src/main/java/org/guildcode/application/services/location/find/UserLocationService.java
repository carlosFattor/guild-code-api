package org.guildcode.application.services.location.find;

import io.smallrye.mutiny.Multi;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.services.location.dto.FindLocationRequestDto;
import org.guildcode.application.services.location.dto.UserLocationResponseDto;
import org.guildcode.application.services.shared.dto.GithubInfoDto;
import org.guildcode.application.services.shared.dto.UserDto;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.user.User;
import org.guildcode.domain.user.services.UserService;
import org.guildcode.infrastructure.data.exceptions.GeolocationException;
import org.guildcode.infrastructure.service.ReactiveServiceMulti;
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
public class UserLocationService implements ReactiveServiceMulti<FindLocationRequestDto> {

    @Inject
    Validator validator;

    @Inject
    UserService userService;

    @Override
    public Multi<ResponseResult> handle(FindLocationRequestDto request) {

        final var validationResults = validator.validate(request);

        if (!validationResults.isEmpty()) {
            return Multi.createFrom().failure(new ConstraintViolationException(validationResults));
        }

        return userService.findUsersByLocation(request.getLng(), request.getLat(), request.getZoom())
                .onItem().transform(toResponse)
                .onFailure(GeolocationException.class)
                .transform(unprocessed -> new ApplicationException((DomainException) unprocessed, StatusCode.UNPROCESSABLE));
    }

    Function<User, ResponseResult> toResponse = (user) -> {
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
        return new ResponseResult(ResponseStatus.ACCEPTED, new UserLocationResponseDto(userDto));
    };
}

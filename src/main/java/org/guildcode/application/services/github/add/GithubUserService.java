package org.guildcode.application.services.github.add;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.domain.token.Tokens;
import org.guildcode.domain.token.service.TokenService;
import org.guildcode.application.services.github.add.dto.*;
import org.guildcode.domain.user.services.GithubService;
import org.guildcode.domain.user.services.UserService;
import org.guildcode.infrastructure.service.ReactiveService;
import org.guildcode.infrastructure.service.result.ResponseResult;
import org.guildcode.infrastructure.service.result.ResponseResults;
import org.guildcode.infrastructure.service.result.ResponseStatus;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequestScoped
public class GithubUserService implements ReactiveService<AddGithubUserRequestDto> {

    @Inject
    Validator validator;
    @Inject
    GithubService githubService;
    @Inject
    UserService userService;
    @Inject
    TokenService tokenService;

    @Override
    public Uni<ResponseResult> handle(AddGithubUserRequestDto githubUserDto) {

        final var validationResults = validator.validate(githubUserDto);

        if (!validationResults.isEmpty()) {
            return Uni.createFrom().failure(new ConstraintViolationException(validationResults));
        }

        return githubService.getGithubUser(githubUserDto.getToken())
                .onItem().transformToUni(gitUser -> userService.findAndParse(gitUser))
                .onItem().transformToUni(userService::updateUser)
                .onItem().transformToUni(tokenService::generateToken)
                .onItem().transform(toResponse);
    }

    Function<Tokens, ResponseResult> toResponse = tokens -> {
        return new ResponseResult(ResponseStatus.OK, new AddGithubUserResponseDto(tokens));
    };
}

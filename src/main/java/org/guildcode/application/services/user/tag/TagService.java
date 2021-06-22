package org.guildcode.application.services.user.tag;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.services.user.tag.dto.UserTagRequestDto;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.user.repository.UserRepository;
import org.guildcode.domain.user.services.UserService;
import org.guildcode.infrastructure.data.exceptions.UserNotFoundException;
import org.guildcode.infrastructure.service.ReactiveService;
import org.guildcode.infrastructure.service.dto.FailureDetailDto;
import org.guildcode.infrastructure.service.dto.FailureResponseDto;
import org.guildcode.infrastructure.service.result.ResponseResult;
import org.guildcode.infrastructure.service.result.ResponseResults;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.function.Function;

@ApplicationScoped
public class TagService implements ReactiveService<UserTagRequestDto> {

    @Inject
    Validator validator;

    @Inject
    UserService userService;

    @Override
    public Uni<ResponseResult> handle(UserTagRequestDto request) {

        final var validationResults = validator.validate(request);

        if (!validationResults.isEmpty()) {
            return Uni.createFrom().failure(new ConstraintViolationException(validationResults));
        }

        return userService.updateUserTags(request.getEmail(), request.getTags().getTags())
                .onItem().transform(toResponse)
                .onFailure(UserNotFoundException.class)
                .transform(notFound -> new ApplicationException((DomainException) notFound, StatusCode.NOT_FOUND));
    }

    Function<Long, ResponseResult> toResponse = (data) -> {
        if(data != null) {
            return ResponseResults.noContent();
        }
        throw new ApplicationException(new UserNotFoundException(), StatusCode.NOT_FOUND);
    };
}

package org.guildcode.application.services.location.update;

import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.services.location.update.dto.UserLocationRequestDto;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.location.Location;
import org.guildcode.domain.location.LocationType;
import org.guildcode.domain.location.services.LocationService;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.infrastructure.data.exceptions.GeolocationException;
import org.guildcode.infrastructure.service.ReactiveService;
import org.guildcode.infrastructure.service.result.ResponseResult;
import org.guildcode.infrastructure.service.result.ResponseResults;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.function.Function;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApplicationScoped
public class UpdateUserLocationService  implements ReactiveService<UserLocationRequestDto> {

    @Inject
    Validator validator;
    @Inject
    LocationService locationService;

    @Override
    public Uni<ResponseResult> handle(UserLocationRequestDto request) {

        final var validationResults = validator.validate(request);

        if (!validationResults.isEmpty()) {
            return Uni.createFrom().failure(new ConstraintViolationException(validationResults));
        }

        var location = new Location(LocationType.POINT.getDescription(), Arrays.asList(request.getLocation().getLng(), request.getLocation().getLat()));

        return locationService.updateLocation(request.getEmail(), location)
                .onItem().transform(toResponse)
                .onFailure(GeolocationException.class)
                .transform(unprocessed -> new ApplicationException((DomainException) unprocessed, StatusCode.UNPROCESSABLE));

    }

    Function<Long, ResponseResult> toResponse = data -> ResponseResults.noContent();
}

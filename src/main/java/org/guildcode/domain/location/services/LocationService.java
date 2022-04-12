package org.guildcode.domain.location.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.location.Location;
import org.guildcode.domain.location.repository.LocationRepository;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.user.User;
import org.guildcode.infrastructure.data.exceptions.GeolocationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationService {

    @Inject
    LocationRepository locationRepository;

    public Uni<Long> updateLocation(String email, Location location) {
        return locationRepository.updateLocationByEmail(email, location)
                .onFailure(GeolocationException.class)
                .transform(geoException -> new ApplicationException((DomainException) geoException, StatusCode.UNPROCESSABLE))
                .onItem().transform(data -> data);
    }
}

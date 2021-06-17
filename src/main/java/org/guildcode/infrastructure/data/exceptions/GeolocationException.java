package org.guildcode.infrastructure.data.exceptions;

import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

public class GeolocationException extends DomainException  {
    public GeolocationException() {
        super(ErrorCode.GEOLOCATION_EXCEPTION);
    }
}

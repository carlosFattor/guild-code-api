package org.guildcode.infrastructure.data.exceptions;

import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

public class IntegrationException extends DomainException {

    public IntegrationException(ErrorCode error, String... message) {
        super(error, message);
    }

    public IntegrationException() {
        super(ErrorCode.GITHUB_USER_NOT_FOUND);
    }
}

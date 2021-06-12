package org.guildcode.infrastructure.data.exceptions;

import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException() {
        super(ErrorCode.GITHUB_USER_NOT_FOUND);
    }
}

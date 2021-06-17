package org.guildcode.infrastructure.data.exceptions;

import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

public class ValidationException extends DomainException {

    public ValidationException() {
        super(ErrorCode.ENTRY_CONSTRAINT_VIOLATION);
    }

    public ValidationException(String... message) {
        super(ErrorCode.ENTRY_CONSTRAINT_VIOLATION, message);
    }
}

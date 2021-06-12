package org.guildcode.infrastructure.service.result;

import java.util.Optional;

public interface Result<T> extends SubjectToError {
    Optional<T> getValue();
}

package org.guildcode.domain.shared.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainException extends RuntimeException {

    String error;
    List<String> details;

    public DomainException(ErrorCode error, String... message) {
        super(joinIfNonNullOrEmpty(message, error.getDescription()));
        this.error = error.getCode();
        if (message != null && message.length > 0) {
            this.details = List.of(message);
        } else {
            this.details = List.of(error.getDescription());
        }
    }

    private static String joinIfNonNullOrEmpty(String[] message, String description) {
        if (message == null || message.length == 0) {
            return description;
        }
        return String.join(" ", message);
    }
}

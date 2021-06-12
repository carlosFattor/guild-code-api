package org.guildcode.application.shared.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.guildcode.domain.shared.exception.DomainException;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationException extends RuntimeException {

    List<String> messages;
    String errorCode;
    StatusCode status;
    Throwable cause;

    public ApplicationException(DomainException domainException, StatusCode status) {
        super(String.join(" ", domainException.getDetails()), domainException);
        this.messages = domainException.getDetails();
        this.errorCode = domainException.getError();
        this.status = status;
        this.cause = domainException;
    }

    public ApplicationException(List<String> messages, String errorCode, StatusCode status, Throwable cause) {
        super(String.join(" ", messages), cause);
        this.messages = messages;
        this.errorCode = errorCode;
        this.status = status;
        this.cause = cause;
    }

    public ApplicationExceptionDto dto() {
        return new ApplicationExceptionDto(messages, errorCode);
    }

    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ApplicationExceptionDto {
        List<String> details;
        String error;
    }
}


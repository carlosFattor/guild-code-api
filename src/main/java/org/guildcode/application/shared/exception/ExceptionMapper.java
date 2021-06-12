package org.guildcode.application.shared.exception;

import lombok.experimental.UtilityClass;
import org.guildcode.domain.shared.exception.DomainException;

import java.util.List;
import java.util.function.Function;

@UtilityClass
public class ExceptionMapper {

    public static final Function<Throwable, ApplicationException> apply() {
        return ex -> {
            if (ex instanceof ApplicationException) {
                return (ApplicationException) ex;
            }
            if (ex instanceof DomainException) {
                return ApplicationException.builder()
                        .status(StatusCode.UNPROCESSABLE)
                        .errorCode(((DomainException) ex).getError())
                        .cause(ex)
                        .messages(((DomainException) ex).getDetails())
                        .build();
            }
            return ApplicationException.builder()
                    .status(StatusCode.INTERNAL_SERVER_ERROR)
                    .cause(ex)
                    .messages(List.of(ex.getMessage()))
                    .build();
        };
    }

}

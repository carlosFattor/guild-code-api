package org.guildcode.infrastructure.data.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.DomainException;
import org.guildcode.domain.shared.exception.ErrorCode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Slf4j
@Provider
public class ExceptionFilter implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof JsonProcessingException) {
            exception = new ApplicationException(List.of("Invalid data supplied for request"), ErrorCode.ENTRY_UNPARSEABLE.getCode(), StatusCode.BAD_REQUEST, exception);
        }
        if (exception instanceof DomainException) {
            log.error("Error on processing this request", exception);
            exception = new ApplicationException(((DomainException) exception).getDetails(), ((DomainException) exception).getError(), StatusCode.UNPROCESSABLE, exception);

        }
        if (!(exception instanceof ApplicationException)) {
            log.error("Error on processing this request", exception);
            exception = new ApplicationException(List.of(exception.getMessage()), "INTERNAL_ERROR", StatusCode.INTERNAL_SERVER_ERROR, exception);

        }
        return Response.status(((ApplicationException) exception).getStatus().getCode())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(((ApplicationException) exception).dto())
                .build();
    }
}

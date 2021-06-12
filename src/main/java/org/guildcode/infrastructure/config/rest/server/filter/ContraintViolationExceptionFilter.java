package org.guildcode.infrastructure.config.rest.server.filter;

import org.guildcode.application.shared.exception.ApplicationException;
import org.guildcode.application.shared.exception.StatusCode;
import org.guildcode.domain.shared.exception.ErrorCode;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.List;
import java.util.stream.Collectors;

public class ContraintViolationExceptionFilter  implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<String> validationMessages = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        var applicationException = new ApplicationException(validationMessages, ErrorCode.ENTRY_CONSTRAINT_VIOLATION.getCode(), StatusCode.UNPROCESSABLE, exception);
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(applicationException.dto())
                .build();
    }
}

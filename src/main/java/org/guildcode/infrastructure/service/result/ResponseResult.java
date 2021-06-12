package org.guildcode.infrastructure.service.result;

import lombok.Data;
import org.guildcode.infrastructure.service.dto.Dto;

import java.util.Objects;
import java.util.Optional;

@Data
public class ResponseResult {

    private final ResponseStatus status;
    private final Dto response;

    public ResponseResult(ResponseStatus status, Dto response) {
        Objects.requireNonNull(status);
        this.status = status;
        this.response = response;
    }

    public ResponseResult(ResponseStatus status) {
        this(status, (Dto)null);
    }

    public ResponseStatus getStatus() {
        return this.status;
    }

    public Optional<Dto> getResponse() {
        return Optional.ofNullable(this.response);
    }
}

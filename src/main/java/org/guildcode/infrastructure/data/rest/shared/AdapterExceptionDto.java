package org.guildcode.infrastructure.data.rest.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.guildcode.infrastructure.config.rest.client.exception.RestClientException;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdapterExceptionDto extends RestClientException {

    String error;

    List<String> details;

    @JsonCreator
    public AdapterExceptionDto(@JsonProperty("error") String error, @JsonProperty("details") List<String> details) {
        super(String.join(" ", details));
        this.error = error;
        this.details = details;
    }
}

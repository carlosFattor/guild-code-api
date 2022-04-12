package org.guildcode.application.services.location.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.guildcode.infrastructure.service.dto.Dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationRequestDto implements Dto {

    @Schema(required = true, description = "Longitude needs to be a value between -180 to 180")
    @NotNull(message = "Longitude value is necessary")
    @Min(value = -180, message = "Value to longitude must be between -180 to 180")
    @Max(value = 180, message = "Value to longitude must be between -180 to 180")
    Double lng;

    @Schema(required = true, description = "Latitude needs to be a value between -90 to 90")
    @NotNull(message = "Latitude value is necessary")
    @Min(value = -90, message = "Value to latitude must be between -90 to 90")
    @Max(value = 90, message = "Value to latitude must be between -90 to 90")
    Double lat;
}

package org.guildcode.entrypoint.v1.geolocation;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.guildcode.application.services.location.dto.LocationRequestDto;

import javax.ws.rs.core.Response;

public interface LocationEntryPoint {

    Uni<Response> update(LocationRequestDto location);

    Multi<Response> findByLatLng(Double lat, Double lng, int zoom);
}

package org.guildcode.entrypoint.v1.user;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.core.Response;

public interface InfoEntrypoint {

    Uni<Response> info();
}

package org.guildcode.entrypoint.v1.tags;

import io.smallrye.mutiny.Uni;
import org.guildcode.application.services.user.tag.dto.TagRequestDto;

import javax.ws.rs.core.Response;

public interface UserTagsEntryPoint {

    Uni<Response> updateUserTags(TagRequestDto tags);
}

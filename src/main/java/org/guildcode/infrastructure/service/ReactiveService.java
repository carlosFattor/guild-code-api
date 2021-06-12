package org.guildcode.infrastructure.service;

import io.smallrye.mutiny.Uni;
import org.guildcode.infrastructure.service.dto.Dto;
import org.guildcode.infrastructure.service.result.ResponseResult;

public interface ReactiveService<RequestDto extends Dto> {
    Uni<ResponseResult> handle(RequestDto request);
}

package org.guildcode.infrastructure.service;

import io.smallrye.mutiny.Multi;
import org.guildcode.infrastructure.service.dto.Dto;
import org.guildcode.infrastructure.service.result.ResponseResult;

public interface ReactiveServiceMulti<RequestDto extends Dto> {
    Multi<ResponseResult> handle(RequestDto request);
}
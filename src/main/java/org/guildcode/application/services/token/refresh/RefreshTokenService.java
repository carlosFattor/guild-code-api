package org.guildcode.application.services.token.refresh;

import io.smallrye.mutiny.Uni;
import lombok.extern.jbosslog.JBossLog;
import org.guildcode.application.services.token.refresh.dto.RefreshTokenRequestDto;
import org.guildcode.infrastructure.service.ReactiveService;
import org.guildcode.infrastructure.service.result.ResponseResult;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@JBossLog
public class RefreshTokenService implements ReactiveService<RefreshTokenRequestDto> {
    @Override
    public Uni<ResponseResult> handle(RefreshTokenRequestDto request) {
        return null;
    }
}

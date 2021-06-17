package org.guildcode.domain.shared.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    ENTRY_UNPARSEABLE("ENTRY-01", "Não foi possível decodificar a mensagem."),
    ENTRY_CONSTRAINT_VIOLATION("ENTRY-02", "Erro por violação de restrição em campos."),
    GITHUB_USER_NOT_FOUND("GITHUB-01", "Github user not found"),
    GITHUB_USER_EMAIL_NOT_FOUND("GITHUB-02", "Email from github not found"),
    GEOLOCATION_EXCEPTION("GEO-01", "Locations is not valid");

    String code;
    String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

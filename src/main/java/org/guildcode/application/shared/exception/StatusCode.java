package org.guildcode.application.shared.exception;

public enum StatusCode {

    NO_CONTENT(204), BAD_REQUEST(400), UNAUTHORIZED(401), FORBIDDEN(403), NOT_FOUND(404), UNPROCESSABLE(422), INTERNAL_SERVER_ERROR(500);

    private int code;

    StatusCode(int code) { this.code = code; }

    public int getCode() {return code; }
}

package org.guildcode.infrastructure.config.rest.client.exception;

import javax.ws.rs.client.ResponseProcessingException;

public class RestClientException extends ResponseProcessingException {

    private int status;

    public RestClientException(String message) {
        super(null, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
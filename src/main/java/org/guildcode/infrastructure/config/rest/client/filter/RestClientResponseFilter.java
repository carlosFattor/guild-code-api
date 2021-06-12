package org.guildcode.infrastructure.config.rest.client.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.guildcode.infrastructure.config.rest.client.exception.CustomException;
import org.guildcode.infrastructure.config.rest.client.exception.RestClientException;
import org.jboss.resteasy.client.jaxrs.internal.ClientRequestContextImpl;

import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Provider
public class RestClientResponseFilter implements ClientResponseFilter {

    @Inject
    private ObjectMapper objectMapper;

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        if (responseContext.hasEntity() && responseContext.getStatus() >= 400) {
            byte[] entity = responseContext.getEntityStream().readAllBytes();
            if (requestContext instanceof ClientRequestContextImpl) {
                try {
                    CustomException annotation = ((ClientRequestContextImpl) requestContext).getInvocation().getClientInvoker().getMethod().getAnnotation(CustomException.class);
                    if (annotation != null) {
                        RestClientException restClientException = objectMapper.readValue(entity, annotation.value());
                        restClientException.setStatus(responseContext.getStatus());
                        throw restClientException;
                    }
                } catch (RestClientException ex) {
                    throw ex;
                } catch (Exception exception) {
                    log.debug("Error deserializing exception", exception);
                }
            }
            throw new RestClientException(responseContext.getStatus() + ": " + new String(entity, StandardCharsets.UTF_8));
        }
    }

}
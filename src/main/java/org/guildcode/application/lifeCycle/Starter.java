package org.guildcode.application.lifeCycle;


import io.quarkus.runtime.StartupEvent;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.guildcode.application.action.TokenGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class Starter {

    @Inject
    Vertx vertx;

    void onStart(@Observes StartupEvent event) {
        vertx.deployVerticle(TokenGenerator.class, new DeploymentOptions().setInstances(3));
    }
}

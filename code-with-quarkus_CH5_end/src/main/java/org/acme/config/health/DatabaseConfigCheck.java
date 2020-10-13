package org.acme.config.health;

import io.quarkus.agroal.runtime.health.DataSourceHealthCheck;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.eclipse.microprofile.health.Readiness;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class DatabaseConfigCheck {
    Logger logger = Logger.getLogger(DatabaseConfigCheck.class.getName());

    @Inject
    @Readiness
    DataSourceHealthCheck dbHealthCheck;

    public void failIfDBisBroken(@Observes StartupEvent startup){
        HealthCheckResponse healthCheckResponse = dbHealthCheck.call();
        if(healthCheckResponse.getState().equals(State.DOWN)){
            logger.warn("Aborting startup: "+healthCheckResponse.getName()+"; message: "+healthCheckResponse.getData());
           throw new IllegalStateException("Datasource connection is broken; Aborting startup");
        }
    }


}

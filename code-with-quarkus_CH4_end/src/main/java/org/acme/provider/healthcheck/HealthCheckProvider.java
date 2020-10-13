package org.acme.provider.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

@ApplicationScoped
public class HealthCheckProvider {

    @Produces
    @Readiness
    @Named("diskSpaceCheck")
    HealthCheck getChecked() {
        return new HealthCheck() {
            @Override
            public HealthCheckResponse call() {
                return HealthCheckResponse.named("diskSpaceCheck").state(isDiskSpaceAvailable()).withData("arbitraryData", "arbitraryValue").build();
            }
        };
    }

    boolean isDiskSpaceAvailable() {
        return Boolean.TRUE;
    }


}
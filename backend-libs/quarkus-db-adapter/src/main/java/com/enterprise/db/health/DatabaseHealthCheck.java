package com.enterprise.db.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@Liveness
@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        try (Connection conn = dataSource.getConnection()) {
            boolean valid = conn.isValid(2);
            return valid
                ? HealthCheckResponse.up("database")
                : HealthCheckResponse.down("database");
        } catch (Exception e) {
            return HealthCheckResponse.named("database")
                    .down()
                    .withData("error", e.getMessage())
                    .build();
        }
    }
}

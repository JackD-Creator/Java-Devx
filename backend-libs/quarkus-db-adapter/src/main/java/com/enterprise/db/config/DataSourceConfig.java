package com.enterprise.db.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import java.util.Optional;

@ApplicationScoped
public class DataSourceConfig {

    private static final Logger LOG = Logger.getLogger(DataSourceConfig.class);

    @ConfigProperty(name = "quarkus.datasource.db-kind", defaultValue = "postgresql")
    String dbKind;

    @ConfigProperty(name = "quarkus.datasource.jdbc.url")
    Optional<String> jdbcUrl;

    @ConfigProperty(name = "quarkus.datasource.jdbc.max-size", defaultValue = "10")
    int maxPoolSize;

    void onStart(@Observes StartupEvent ev) {
        LOG.infof("DB Adapter started — kind: %s, pool-max: %d", dbKind, maxPoolSize);
        jdbcUrl.ifPresent(url -> {
            String sanitized = url.replaceAll("password=[^&]+", "password=***");
            LOG.infof("JDBC URL: %s", sanitized);
        });
    }
}

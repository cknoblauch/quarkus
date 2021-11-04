package io.quarkus.flyway.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

public class FlywayExtensionMigrateAtStartTest {
    // Quarkus built object
    @Inject
    Flyway flyway;

    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addAsResource("db/migration/V1.0.0__Quarkus.sql")
                    .addAsResource("migrate-at-start-config.properties", "application.properties"));

    @Test
    @DisplayName("Migrates at start correctly")
    public void testFlywayConfigInjection() {
        MigrationInfo migrationInfo = flyway.info().current();
        assertNotNull(migrationInfo, "No Flyway migration was executed");

        String currentVersion = migrationInfo
                .getVersion()
                .toString();

        assertEquals("1.0.0", currentVersion);
    }
}

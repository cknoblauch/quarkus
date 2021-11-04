package io.quarkus.hibernate.orm.panache.deployment.test.multiple_pu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.hibernate.orm.panache.deployment.test.multiple_pu.first.FirstEntity;
import io.quarkus.hibernate.orm.panache.deployment.test.multiple_pu.second.SecondEntity;
import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;

public class DefaultPersistenceUnitConfigTest {

    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .withApplicationRoot((jar) -> jar
                    .addClasses(FirstEntity.class, SecondEntity.class, PanacheTestResource.class)
                    .addAsResource("application-test.properties", "application.properties"));

    @Test
    public void panacheOperations() {
        /**
         * First entity operations
         */
        RestAssured.when().get("/persistence-unit/first/name-1").then().body(Matchers.is("1"));
        RestAssured.when().get("/persistence-unit/first/name-2").then().body(Matchers.is("2"));

        /**
         * second entity operations
         */
        RestAssured.when().get("/persistence-unit/second/name-1").then().body(Matchers.is("1"));
        RestAssured.when().get("/persistence-unit/second/name-2").then().body(Matchers.is("2"));
    }

    @Test
    void entityManagerShouldExist() {
        FirstEntity firstEntity = new FirstEntity();
        assertNotNull(firstEntity.getEntityManager());

        SecondEntity secondEntity = new SecondEntity();
        assertNotNull(secondEntity.getEntityManager());
    }
}

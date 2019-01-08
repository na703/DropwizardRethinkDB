package com.dropwizardrethinkdb.service.resources;

import com.dropwizardrethinkdb.service.ExampleServiceApplication;
import com.dropwizardrethinkdb.service.ExampleServiceConfiguration;
import com.dropwizardrethinkdb.service.api.Person;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class HelloWorldTest {

    @ClassRule
    public static final DropwizardAppRule<ExampleServiceConfiguration> RULE =
            new DropwizardAppRule<ExampleServiceConfiguration>(ExampleServiceApplication.class,
                    ResourceHelpers.resourceFilePath("test-config.yml"));

    @Test
    public void testHelloWorld() throws Exception {
        final String name = "Dr. IntegrationTest";
        final Person greeting = RULE.client().target("http://localhost:" + RULE.getLocalPort() + "/hello-world")
                .queryParam("name", name)
                .request()
                .get(Person.class);
        assertThat(greeting.getName()).isEqualTo(name);
    }
}

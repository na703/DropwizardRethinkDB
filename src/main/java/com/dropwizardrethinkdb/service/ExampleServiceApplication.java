package com.dropwizardrethinkdb.service;

import com.dropwizardrethinkdb.service.api.User;
import com.dropwizardrethinkdb.service.auth.CoreAuthenticator;
import com.dropwizardrethinkdb.service.auth.CoreAuthorizer;
import com.dropwizardrethinkdb.service.db.AccessDAO;
import com.dropwizardrethinkdb.service.db.AuthTokenDAO;
import com.dropwizardrethinkdb.service.db.RethinkDBConn;
import com.dropwizardrethinkdb.service.db.UserDAO;
import com.dropwizardrethinkdb.service.health.RethinkDBHealthCheck;
import com.dropwizardrethinkdb.service.resources.AuthenticationResource;
import com.dropwizardrethinkdb.service.resources.CoreServiceHealthCheck;
import com.dropwizardrethinkdb.service.resources.HelloWorldResource;
import com.dropwizardrethinkdb.service.resources.UserResource;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class ExampleServiceApplication extends Application<ExampleServiceConfiguration> {

    public static final RethinkDB r = RethinkDB.r;
    private static final String BEARER = "Bearer";

    public static void main(final String[] args) throws Exception {
        new ExampleServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "CoreService";
    }

    @Override
    public void initialize(final Bootstrap<ExampleServiceConfiguration> bootstrap) {

        bootstrap.addBundle(new SwaggerBundle<ExampleServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ExampleServiceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final ExampleServiceConfiguration configuration,
                    final Environment environment) {

        Connection conn = r.connection().hostname(configuration.rethinkdb.getRethinkHost())
                .db(configuration.rethinkdb.getRethinkDatabase()).port(configuration.rethinkdb.getRethinkPort()).connect();
        RethinkDBConn rethinkDbCon = new RethinkDBConn(r, conn);
//         RethinkManaged rethinkmanaged = new RethinkManaged(r, conn);
//         environment.lifecycle().manage(rethinkmanaged);


        UserDAO userDAO = new UserDAO(rethinkDbCon);
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(rethinkDbCon);
        AccessDAO accessDAO = new AccessDAO(authTokenDAO, userDAO);


        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new UserResource(userDAO, accessDAO));
        environment.jersey().register(new AuthenticationResource(authTokenDAO, userDAO));
        environment.healthChecks().register("healthCheck",  new CoreServiceHealthCheck());
        environment.healthChecks().register("RethinkDB",new RethinkDBHealthCheck(configuration));

        //TODO: caching authenticator
        //CachingAuthenticator<String, User> cachingAuthenticator = new CachingAuthenticator<>(
        //        environment.metrics(), new CoreAuthenticator(authTokenDAO), configuration.getAuthenticationCachePolicy());

        // Register OAuth authentication
        environment.jersey()
                .register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new CoreAuthenticator(authTokenDAO))
                        .setAuthorizer(new CoreAuthorizer())
                        .setPrefix(BEARER)
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

    }

}

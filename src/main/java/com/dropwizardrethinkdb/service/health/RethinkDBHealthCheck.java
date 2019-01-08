package com.dropwizardrethinkdb.service.health;



import com.dropwizardrethinkdb.service.ExampleServiceConfiguration;
import com.codahale.metrics.health.HealthCheck;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class RethinkDBHealthCheck extends HealthCheck {

    private static final RethinkDB rdb = RethinkDB.r;
    private final ExampleServiceConfiguration ExampleServiceConfiguration;

    public RethinkDBHealthCheck(ExampleServiceConfiguration ExampleServiceConfiguration) {
        this.ExampleServiceConfiguration = ExampleServiceConfiguration;
    }

    @Override
    protected Result check() throws Exception {
        Connection conn = rdb.connection()
                .hostname(ExampleServiceConfiguration.rethinkdb.getRethinkHost())
                .port(ExampleServiceConfiguration.rethinkdb.getRethinkPort()).connect();
        String resp = rdb.table(ExampleServiceConfiguration.rethinkdb.getDefaultTable()).status().run(conn);
        if(resp.contains(ExampleServiceConfiguration.rethinkdb.getRethinkDatabase())){
            return Result.healthy();
        }
        return Result.unhealthy("cannot connect to RethinkDB...");
    }
}

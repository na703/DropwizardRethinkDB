package com.dropwizardrethinkdb.service.resources;

import com.codahale.metrics.health.HealthCheck;

public class CoreServiceHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}

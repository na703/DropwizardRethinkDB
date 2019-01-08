package com.dropwizardrethinkdb.service.db;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import io.dropwizard.lifecycle.Managed;

/*
* connection pooling not required for rethinkdb
* */
@Deprecated
public class RethinkManaged implements Managed {


    private RethinkDB rethinkdb;
    protected Connection connection;

    public RethinkManaged(RethinkDB rethinkdb, Connection connection) {
        this.rethinkdb = rethinkdb;
        this.connection = connection;
    }


    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        rethinkdb.connection().connect().close();
    }
}

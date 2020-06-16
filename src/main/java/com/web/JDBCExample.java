package com.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.jdbc.JDBCClient;


public class JDBCExample extends AbstractVerticle {

    @Override
    public void start(Promise<Void> stopPromise) throws Exception{

final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
                                .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
                                .put("driver_class", "org.hsqldb.jdbcDriver")
                                .put("max_pool_size", 30)
                                .put("user", "SA")
                                .put("password", ""));

            client.getConnection(conn -> {
                if (conn.failed()){
                    System.err.println(conn.cause().getMessage());
                    return;
                }

                final SQLConnection connection = conn.result();
                connection.execute("create table test(id int primary key, name varchar(255))", res -> {
                   if (res.failed()){
                       throw new RuntimeException(res.cause());
                   }
//                   inserting new data in test table
                    connection.execute("insert into test values(1, 'Hello')", insert -> {
//                        query some data
                        connection.query("Select * from test", rs -> {
                           for (JsonArray line: rs.result().getResults()){
                               System.out.println(line.encode());
                           }

//                           Closing the connection
                            connection.close(done ->{
                               if (done.failed()){
                                   throw new RuntimeException(done.cause());
                               }
                            });
                        });
                    });
                });
                    }

            );

    }

    @Override
    public void stop(Promise<Void> stopPromise){

    }
}

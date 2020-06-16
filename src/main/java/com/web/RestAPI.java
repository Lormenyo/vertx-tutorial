package com.web;

import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.stream.Collectors;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class RestAPI extends AbstractVerticle {
    private FreeMarkerTemplateEngine templateEngine;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        startHttpServer();
    }


    private Future<Void> startHttpServer() throws Exception {
        Promise<Void> promise = Promise.promise();
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/").handler(this::indexHandler);

        templateEngine = FreeMarkerTemplateEngine.create(vertx);

        server.requestHandler(router::accept);
        server.listen(5000, ar -> {
            if (ar.succeeded()) {
                System.out.println("HTTP Server running on port 5000");
                promise.complete();
            } else {
                System.out.println("HTTP could not start");
                promise.fail(ar.cause());
            }
        });

        return promise.future();
    }

    private void indexHandler(RoutingContext context) {

        final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
                .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
                .put("driver_class", "org.hsqldb.jdbcDriver")
                .put("max_pool_size", 30)
                .put("user", "SA")
                .put("password", ""));

        client.getConnection(conn -> {
            if (conn.failed()) {
                System.err.println(conn.cause().getMessage());
                return;
            }

            SQLConnection connection = conn.result();
            connection.query("Select * from test", res -> {
                List<String> content = res.result()
                        .getResults()
                        .stream()
                        .map(json -> json.getString(0))
                        .sorted()
                        .collect(Collectors.toList());


//                           Closing the connection
                connection.close(done -> {
                    if (done.failed()) {
                        throw new RuntimeException(done.cause());
                    }
                    System.out.println("Connection Closed");
                    context.put("title", "Vertx API Tutorial");
                    context.put("content", content);

                    templateEngine.render(context.data(), "templates/index.ftl", ar ->{
                        if (ar.succeeded()){
                            context.response().putHeader("Content-Type", "text/html");
                            context.response().end("succeeded");
                        }
                    });
                });
            });
        });

    }
}

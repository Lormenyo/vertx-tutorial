package com.web;


import com.tutorial.AnotherVerticle;
import com.tutorial.MyVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class MainVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        //        deploying the verticles
        vertx.deployVerticle(new MyVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("MyVerticle: Deployment complete");
            }
        });

//                Thread.sleep(3000);

        vertx.deployVerticle(new AnotherVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("Another Verticle: Deployment complete");
            }
        });

        vertx.deployVerticle(new JDBCExample(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("JDBC Verticle: Deployment complete");
            }
        });

        vertx.deployVerticle(new RestAPI(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("RestAPI Verticle: Deployment complete");
            }
        });


    }



//        once verticle is deployed the verticle's start method is called

}

package com.tutorial;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;


public  class VertxApp{

    public static void main(String[] args) throws InterruptedException {
//        Vertx vertx = Vertx.vertx();
//        This vertx instance creates a number of threads internally to handle
//        the exchange of messages between verticles.
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
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

            } else {
                System.out.println("Failed: " + res.cause());
            }
        });


//        once verticle is deployed the verticle's start method is called

    }
}
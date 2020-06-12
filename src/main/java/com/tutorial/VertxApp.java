package com.tutorial;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;


public  class VertxApp{

    public static void main(String[] args) throws InterruptedException {
        Vertx vertx = Vertx.vertx();
//        This vertx instance creates a number of threads internally to handle
//        the exchange of messages between verticles.

//        deploying the verticles
        vertx.deployVerticle(new AnotherVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("Another Verticle: Deployment complete");
            }
        });

        vertx.deployVerticle(new MyVerticle(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> stringAsyncResult) {
                System.out.println("MyVerticle: Deployment complete");
            }
        });
//        once verticle is deployed the verticle's start method is called

    }
}
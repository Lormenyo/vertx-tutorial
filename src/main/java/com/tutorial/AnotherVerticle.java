package com.tutorial;

import io.vertx.core.AbstractVerticle;
import  io.vertx.core.Vertx;
import io.vertx.core.Future;



public class AnotherVerticle extends AbstractVerticle {
    Vertx vertx = Vertx.vertx();

    public void start(Future<Void> startFuture) throws Exception {
            System.out.println("Another verticle has started.");
//        publish() sends message to all listening verticles
//        send() sends message to just one listening verticle
        vertx.eventBus().publish("anAddress", "Hannah here 2");
        vertx.eventBus().send   ("anAddress", "Lormenyo 1");

    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("Another verticle has stopped");
    }
}

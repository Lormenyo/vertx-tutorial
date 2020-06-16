package com.tutorial;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;


public class AnotherVerticle extends AbstractVerticle {


    public void start(Promise<Void> startPromise) throws Exception {
            System.out.println("Another verticle has started.");
//        publish() sends message to all listening verticles
//        send() sends message to just one listening verticle
        vertx.eventBus().publish("anAddress", "Hannah here 2");
        vertx.eventBus().send   ("anAddress", "Lormenyo 1");

    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        System.out.println("Another verticle has stopped");
    }
}

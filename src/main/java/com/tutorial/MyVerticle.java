package com.tutorial;

import  io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;


public  class MyVerticle extends AbstractVerticle {

//    START METHOD VERSION 1
    @Override
    public void start() throws Exception{
        System.out.println("starting verticle");
//        This is where the verticle is initialized.
//        You can create HTTP/TCP server, register event handlers
//        on the event bus, deploy other verticles or whatever your
//        verticle needs to do.
    }

//    START METHOD VERSION 2
    @Override
    public void start(Future<Void> startFuture) {
        System.out.println("My verticle has started");
//        This asynchronously tells Vertx that the verticle has been deployed
//        succesfully.
    }

    @Override
    public void stop(Future stopFuture) throws Exception {
        System.out.println("My verticle has stopped");
    }
}

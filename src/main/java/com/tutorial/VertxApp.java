package com.tutorial;

import com.web.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;


public  class VertxApp extends AbstractVerticle {

    public static void main(String[] args) throws Exception {

        Vertx vertx = Vertx.vertx();
//        This vertx instance creates a number of threads internally to handle
//        the exchange of messages between verticles.
        vertx.deployVerticle(new MainVerticle());
    }

}
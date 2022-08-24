package com.grpc.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9999;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new CustomerServiceImpl()).build();

        server.start();
        System.out.println("Server has been started on port: " + port);
        server.awaitTermination();
    }
}

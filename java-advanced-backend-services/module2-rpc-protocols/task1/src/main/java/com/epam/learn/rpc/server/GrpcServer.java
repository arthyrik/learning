package com.epam.learn.rpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;

import static java.util.concurrent.TimeUnit.SECONDS;

public class GrpcServer {

    private final int port;
    private final Server server;

    public GrpcServer(int port) {
        this.port = port;
        server = ServerBuilder.forPort(port)
                .addService(new MessageService())
                .build();
    }

    @SneakyThrows
    public void start() {
        server.start();
        System.out.println("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    @SneakyThrows
    private void stop() {
        if (server != null) {
            System.out.println("Shutting down server");
            server.shutdown();
            server.awaitTermination(10, SECONDS);
        }
    }
}

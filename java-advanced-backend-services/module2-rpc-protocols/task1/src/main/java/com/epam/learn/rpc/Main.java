package com.epam.learn.rpc;

import com.epam.learn.rpc.client.GrpcClient;
import com.epam.learn.rpc.server.GrpcServer;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import lombok.SneakyThrows;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        var server = new GrpcServer(8080);
        server.start();

        var channel = Grpc.newChannelBuilder("localhost:8080", InsecureChannelCredentials.create()).build();
        var client = new GrpcClient(channel);

        while (true) {
            System.out.println(client.ping());
            Thread.sleep(SECONDS.toMillis(2));
        }
    }
}
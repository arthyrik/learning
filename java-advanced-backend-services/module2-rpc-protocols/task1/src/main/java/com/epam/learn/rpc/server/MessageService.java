package com.epam.learn.rpc.server;

import com.epam.learn.rpc.Message;
import com.epam.learn.rpc.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;

public class MessageService extends MessageServiceGrpc.MessageServiceImplBase {

    @Override
    public void ping(Message request, StreamObserver<Message> responseObserver) {
        System.out.println(request.getMessage());

        var response = Message.newBuilder()
                .setMessage("pong")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

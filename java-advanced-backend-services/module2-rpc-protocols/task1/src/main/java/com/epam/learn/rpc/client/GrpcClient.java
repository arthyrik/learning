package com.epam.learn.rpc.client;

import com.epam.learn.rpc.Message;
import com.epam.learn.rpc.MessageServiceGrpc;
import io.grpc.Channel;

public class GrpcClient {

    private final MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub;

    public GrpcClient(Channel channel) {
        this.messageServiceBlockingStub = MessageServiceGrpc.newBlockingStub(channel);
    }

    public String ping() {
        var request = Message.newBuilder()
                .setMessage("ping")
                .build();
        var response = messageServiceBlockingStub.ping(request);

        return response.getMessage();
    }
}

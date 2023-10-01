package org.example.broker.service.impl;

import com.example.grpc.Subscribe.SubscribeRequest;
import com.example.grpc.Subscribe.SubscribeResponse;
import com.example.grpc.SubscriberGrpc;
import io.grpc.stub.StreamObserver;
import org.example.broker.model.SubscriberConnection;
import org.example.broker.service.ConnectionStorageService;

public class SubscriberService extends SubscriberGrpc.SubscriberImplBase {
  private final ConnectionStorageService connectionStorageService;

  public SubscriberService() {
    this.connectionStorageService = ConnectionStorageServiceImpl.getInstance();
  }

  @Override
  public void subscribe(SubscribeRequest request, StreamObserver<SubscribeResponse> responseObserver) {
    SubscriberConnection connection = new SubscriberConnection(request);
    connectionStorageService.add(connection);

    SubscribeResponse response = SubscribeResponse.newBuilder()
        .setIsSuccess(Boolean.TRUE)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}

package org.example.subscriber.service;

import com.example.grpc.NotifierGrpc;
import com.example.grpc.Notify.NotifyRequest;
import com.example.grpc.Notify.NotifyResponse;
import io.grpc.stub.StreamObserver;

public class NotificationService extends NotifierGrpc.NotifierImplBase {

  @Override
  public void notify(NotifyRequest request, StreamObserver<NotifyResponse> responseObserver) {
    System.out.println("Received content from topic:" + request.getContent());

    NotifyResponse response = NotifyResponse.newBuilder()
        .setIsSuccess(Boolean.TRUE)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}

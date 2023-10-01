package org.example.broker;

import com.example.grpc.PublisherGrpc;
import com.example.grpc.Service.PublishRequest;
import com.example.grpc.Service.PublishResponse;
import io.grpc.stub.StreamObserver;
import org.example.broker.model.Message;
import org.example.broker.service.MessageStorageService;
import org.example.broker.service.impl.MessageStorageServiceImpl;

public class PublisherService extends PublisherGrpc.PublisherImplBase {
  private final MessageStorageService messageStorageService;
  public PublisherService() {
    this.messageStorageService = MessageStorageServiceImpl.getInstance();
  }

  @Override
  public void publishMessage(PublishRequest request,
      StreamObserver<PublishResponse> responseObserver) {
    System.out.println("Received: " +  request.getTopic() +  ", " + request.getContent());
    Message message = new Message(request.getTopic(), request.getContent());
    messageStorageService.add(message);
    PublishResponse response = PublishResponse.newBuilder()
        .setIsSuccess(Boolean.TRUE)
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}

package org.example.sender;

import com.example.grpc.Service.PublishResponse;
import io.grpc.stub.StreamObserver;

class SenderCallback implements StreamObserver<PublishResponse> {

  @Override
  public void onNext(PublishResponse value) {
    System.out.println("Received product" + value);
  }

  @Override
  public void onError(Throwable cause) {
    System.out.println("Error occurred, cause:" + cause.getMessage());
  }

  @Override
  public void onCompleted() {
    System.out.println("Stream completed");
  }
}
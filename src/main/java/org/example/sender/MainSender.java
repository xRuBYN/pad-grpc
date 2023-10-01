package org.example.sender;

import com.example.grpc.PublisherGrpc;
import com.example.grpc.Service.PublishRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Scanner;

public class MainSender {
  public static final String TARGET_NAME = "localhost:8080";

  public static void main(String[] args) {
    System.out.println("Publisher");
    ManagedChannel channel = ManagedChannelBuilder.forTarget(TARGET_NAME)
        .usePlaintext()
        .build();

    var client = PublisherGrpc.newBlockingStub(channel);
    Scanner scanner = new Scanner(System.in);
    while (Boolean.TRUE) {
      System.out.println("Enter the topic: ");
      String topic = scanner.next();
      System.out.println("Enter the content: ");
      String content = scanner.next();
      PublishRequest request = PublishRequest.newBuilder()
          .setTopic(topic)
          .setContent(content)
          .build();
      try {
        var response = client.publishMessage(request);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

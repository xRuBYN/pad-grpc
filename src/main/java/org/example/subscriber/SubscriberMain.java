package org.example.subscriber;

import static org.example.sender.MainSender.TARGET_NAME;

import com.example.grpc.Subscribe.SubscribeRequest;
import com.example.grpc.SubscriberGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.Scanner;
import org.example.subscriber.service.NotificationService;

public class SubscriberMain {
  public static final int PORT = 0;

  public static void main(String[] args) throws Exception {
    Server server = ServerBuilder.forPort(PORT)
        .addService(new NotificationService())
        .build();
    server.start();
    subscribe(server);
    System.out.println(server.getPort());
    System.out.println("Subscriber started.");
    server.awaitTermination();
  }

  private static void subscribe(Server server) {
    ManagedChannel channel = ManagedChannelBuilder.forTarget(TARGET_NAME)
        .usePlaintext()
        .build();
    var client = SubscriberGrpc.newBlockingStub(channel);

    String port = String.valueOf(server.getPort());

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the topic: ");
    String topic = scanner.next();
    var request = SubscribeRequest.newBuilder()
        .setAddress(port)
        .setTopic(topic)
        .build();
    try {
      var response = client.subscribe(request);
      System.out.println(response.getIsSuccess());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }
}

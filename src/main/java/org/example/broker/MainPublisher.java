package org.example.broker;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.broker.service.impl.SenderWorkerServiceImpl;
import org.example.broker.service.impl.SubscriberService;

public class MainPublisher {
  public static final int PORT = 8080;

  private static SenderWorkerServiceImpl senderWorkerService = new SenderWorkerServiceImpl();

  public static void main(String[] args) throws Exception {
    senderWorkerService.asyncTask();
    Server server = ServerBuilder.forPort(PORT)
        .addService(new PublisherService())
        .addService(new SubscriberService())
        .build();
    server.start();
    System.out.println("Server started.");
    server.awaitTermination();
  }
}

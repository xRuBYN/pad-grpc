package org.example.broker.service.impl;

import static java.util.Objects.nonNull;

import com.example.grpc.NotifierGrpc;
import com.example.grpc.Notify.NotifyRequest;
import com.example.grpc.Notify.NotifyResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.example.broker.service.ConnectionStorageService;
import org.example.broker.service.MessageStorageService;

public class SenderWorkerServiceImpl  {
  public static final int CORE_POOL_SIZE = 1;
  public static final int PERIOD = 5;
  public static final int INITIAL_DELAY = 0;
  public static final String UNAVAILABLE = "UNAVAILABLE: io exception";
  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(CORE_POOL_SIZE);
  private final ConnectionStorageService connectionStorageService;
  private final MessageStorageService messageStorageService;
  public SenderWorkerServiceImpl() {
    this.connectionStorageService = ConnectionStorageServiceImpl.getInstance();
    this.messageStorageService = MessageStorageServiceImpl.getInstance();
  }

  public void asyncTask() {
    executor.scheduleAtFixedRate(() -> {
      while (!messageStorageService.isEmpty()) {
        var message = messageStorageService.getNext();
        if(nonNull(message)) {
          var connections = connectionStorageService.getConnections(message.getTopic());
          connections.forEach(connection -> {
            var client = NotifierGrpc.newBlockingStub(connection.getGrpcChannel());
            var request = NotifyRequest.newBuilder()
                .setContent(message.getContent())
                .build();
            try {
              NotifyResponse response = client.notify(request);
              System.out.println(response.getIsSuccess());
            } catch (Exception e) {
              if (UNAVAILABLE.equals(e.getMessage())) {
                connectionStorageService.remove(connection.getAddress());
              } else {
                System.out.println(e.getMessage());
              }
            }
          });
        }
      }
      System.out.println("Background task executed!");
    }, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    stopAsync();
  }

  private void stopAsync() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      executor.shutdown();
      try {
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
          executor.shutdownNow();
        }
      } catch (InterruptedException e) {
        executor.shutdownNow();
      }
    }));
  }


}

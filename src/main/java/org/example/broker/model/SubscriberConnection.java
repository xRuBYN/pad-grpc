package org.example.broker.model;

import static org.example.sender.MainSender.TARGET_NAME;

import com.example.grpc.Subscribe.SubscribeRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SubscriberConnection {
  public static final String LOCALHOST = "localhost:";
  private String address;
  private String topic;
  private ManagedChannel channel;

  public SubscriberConnection(SubscribeRequest request) {
    this.address = request.getAddress();
    this.topic = request.getTopic();
    this.channel = ManagedChannelBuilder.forTarget(LOCALHOST + Integer.valueOf(address))
        .usePlaintext()
        .build();
  }

  public SubscriberConnection(String address, String topic) {
    this.address = address;
    this.topic = topic;
  }

  public String getTopic() {
    return topic;
  }

  public String getAddress() {
    return address;
  }

  public ManagedChannel getGrpcChannel() {
    return channel;
  }
}

package org.example.broker.service.impl;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.broker.model.SubscriberConnection;
import org.example.broker.service.ConnectionStorageService;

public class ConnectionStorageServiceImpl implements ConnectionStorageService {
  public List<SubscriberConnection> connections;
  private static ConnectionStorageServiceImpl connectionStorageService;
  private ConnectionStorageServiceImpl() {
    connections = Collections.synchronizedList(new ArrayList<>());
  }

  public static ConnectionStorageService getInstance() {
    if(isNull(connectionStorageService)) {
      connectionStorageService = new ConnectionStorageServiceImpl();
      return connectionStorageService;
    } else
      return connectionStorageService;
  }


  @Override
  public void add(SubscriberConnection connection) {
    System.out.println("Added connection:" + connection.getAddress());
    connections.add(connection);
  }

  @Override
  public void remove(String address) {
    connections.removeIf(x -> x.getAddress().equals(address));
  }

  @Override
  public List<SubscriberConnection> getConnections(String topic) {
    return connections.stream()
        .filter(x -> x.getTopic().equals(topic))
        .toList();
  }
}

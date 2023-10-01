package org.example.broker.service;

import java.util.List;
import org.example.broker.model.SubscriberConnection;

public interface ConnectionStorageService {

  void add(SubscriberConnection connection);
  void remove(String address);
  List<SubscriberConnection> getConnections(String topic);

}

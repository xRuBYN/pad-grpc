package org.example.broker.service;

import org.example.broker.model.Message;

public interface MessageStorageService {
  void add(Message message);
  Message getNext();

  Boolean isEmpty();
}

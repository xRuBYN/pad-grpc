package org.example.broker.service.impl;

import static java.util.Objects.isNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.example.broker.model.Message;
import org.example.broker.service.MessageStorageService;

public class MessageStorageServiceImpl implements MessageStorageService {
  private Queue<Message> messages;
  private static MessageStorageServiceImpl messageStorageService;
  private MessageStorageServiceImpl() {
    this.messages = new ConcurrentLinkedDeque<>();
  }

  public static MessageStorageService getInstance() {
    if(isNull(messageStorageService)) {
      messageStorageService = new MessageStorageServiceImpl();
      return messageStorageService;
    } else {
      return messageStorageService;
    }
  }

  @Override
  public void add(Message message) {
    System.out.println("Added message:" + message.getTopic() + " " + message.getContent());
    messages.add(message);
  }

  @Override
  public Message getNext() {
    return messages.poll();
  }

  @Override
  public Boolean isEmpty() {
    return messages.isEmpty();
  }
}

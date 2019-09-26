package org.apache.pricecontroller.api;

public interface MessageService {

    boolean send(String from, String to, String content);
}

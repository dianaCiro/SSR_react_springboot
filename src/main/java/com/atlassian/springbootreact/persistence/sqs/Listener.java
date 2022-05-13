package com.atlassian.springbootreact.persistence.sqs;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @SqsListener(value = "${cloud.aws.queue.name}")
    public void receiveMessage(String message) {

       System.out.println("the message was received:  " + message);

    }
}

package com.atlassian.springbootreact.persistence.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.atlassian.springbootreact.domain.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class Publisher implements PublisherRepository {

    private AmazonSQSAsync amazonSQSAsync;

    @Value("${cloud.aws.queue.complete-uri}")
    private String sqsUrl;

    public Publisher(AmazonSQSAsync amazonSQSAsync) {
        this.amazonSQSAsync = amazonSQSAsync;
    }

    public void sendMessage(String message) {
        SendMessageRequest messageRequest = new SendMessageRequest(sqsUrl,  message);

        amazonSQSAsync.sendMessage(messageRequest);
    }
}

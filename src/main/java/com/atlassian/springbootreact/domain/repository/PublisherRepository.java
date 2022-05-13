package com.atlassian.springbootreact.domain.repository;

public interface PublisherRepository {
    void sendMessage(String message);
}

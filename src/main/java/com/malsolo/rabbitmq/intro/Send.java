package com.malsolo.rabbitmq.intro;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    static Logger logger = LoggerFactory.getLogger(Send.class);

    private final static String QUEUE_NAME = "hello_from_java";

    public static void main(String[] args) throws IOException, TimeoutException {
        logger.info("Java sender begins");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello from Java";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        channel.close();
        connection.close();
        logger.warn("Java sender ends");
    }
}

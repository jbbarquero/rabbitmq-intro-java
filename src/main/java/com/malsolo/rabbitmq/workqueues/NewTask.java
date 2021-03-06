package com.malsolo.rabbitmq.workqueues;

import com.google.common.base.Joiner;
import com.malsolo.rabbitmq.Queues;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {

    static Logger logger = LoggerFactory.getLogger(NewTask.class);

    public static void main(String[] args) throws IOException, TimeoutException {
        logger.info("Java work queues begins");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        boolean durable = true; //Because the consumer is also durable. Remove the queue if it already exists and it was created not durable
        channel.queueDeclare(Queues.work_queue.name(), durable, false, false, null);

        String message = getMessage(args);

        //Mark our messages as persisten
        channel.basicPublish("", Queues.work_queue.name(), MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
        logger.warn("Java work queues ends");
    }

    private static String getMessage(String[] strings) {
        if (strings == null || strings.length < 1) {
            return "Hello World!";
        }
        Joiner joiner = Joiner.on(" ").skipNulls();
        return joiner.join(strings);
    }
}

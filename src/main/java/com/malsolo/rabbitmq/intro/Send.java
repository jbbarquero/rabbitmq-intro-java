package com.malsolo.rabbitmq.intro;

import com.malsolo.rabbitmq.Queues;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final int MAX_NUMBER = 100;

    static Logger logger = LoggerFactory.getLogger(Send.class);

    public static void main(String[] args) throws IOException, TimeoutException {

        logger.info("Java sender begins");
        if (args.length != 1) {
            System.out.println("Incorrect arguments. Usage Send number_of_events");
            return;
        }
        int number;
        try {
            number = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe) {
            System.out.printf("Incorrect argument %s. Usage Send number_of_events%n", args[0]);
            return;
        }
        if (number > MAX_NUMBER) {
            System.out.printf("Incorrect argument, too big: %d, must be lower than %d. Usage Send number_of_events%n"
                    , number, MAX_NUMBER);
            return;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Queues.hello_queue.name(), false, false, false, null);
        String message = "Hello from Java";

        for (int i = 0; i < number; i++) {
            String theMessage = String.format("%s: %d", message, i);
            channel.basicPublish("", Queues.hello_queue.name(), null, theMessage.getBytes());
            logger.debug("Published {}", theMessage);
        }

        channel.close();
        connection.close();
        logger.warn("Java sender ends");
    }
}

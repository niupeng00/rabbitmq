package com.rabbitmq.arbbitmq.simple;

import com.rabbitmq.arbbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中获取一个通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String msg = "hello simple";

        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        System.out.println(String.format("----send send!%s", msg));



        channel.close();
        connection.close();
    }
}

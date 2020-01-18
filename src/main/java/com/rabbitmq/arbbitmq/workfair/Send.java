package com.rabbitmq.arbbitmq.workfair;

import com.rabbitmq.arbbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    /**
     *                      |--- C1
     *  P ----- Queue ----- |--- C2
     *                      |--- C3
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    private static final String QUEUE_NAME = "test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取 channel
        Channel channel = connection.createChannel();
        // 声明队列
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        /**
         * 一次只发一条消息给你消费者，处理完成后，消费者返回结果，再次发送一条数据
         * 限制 MQ 发送给同一个消费者 不得超过一条信息 basicQos
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        // 同时发送 50 条数据
        for (int i = 0; i < 50; i++) {
            String msg = "hello " + i;
            System.out.println("[WQ]：send：" + msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(1 * 20);
        }
        channel.close();
        connection.close();
    }
}

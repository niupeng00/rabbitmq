package com.rabbitmq.arbbitmq.workfair;

import com.rabbitmq.arbbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    /**
     * 公平分发 C1 处理完成后，通知 MQ 在发一条，必须关闭自动应答 autoAck
     *  1、channel.basicQos(1); // 一次只分发一条消息
     *  2、channel.basicAck(envelope.getDeliveryTag(), false); // 成功后，手动返回给 MQ 回执消息
     *  3、boolean autoAck = false; // 关闭自动应答
     */
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取channel
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 一次只分发一条消息
        channel.basicQos(1);
        // 定义一个消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 消息打到，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("[2]: Recv msg:" + msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[3]: done");
                    // 成功后，手动返回给 MQ 回执消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };

        // 关闭自动应答
        boolean autoAck = false;
        // 监听队列
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);


    }
}

package com.rabbitmq.arbbitmq.ps;

import com.rabbitmq.arbbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    // 队列名称
    private static final String QUEUE_NAME = "test_queue_fanout_sms";
    // 交换机 名称
    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtils.getConnection();
        // 获取channel
        final Channel channel = connection.createChannel();
        // 绑定到交换机或转发器
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
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
                    System.out.println("[2]: done");
                    // 成功后，手动返回给 MQ 回执消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };
        // 关闭自动应答 Message acknowkedgment 简称 autoAck
        boolean autoAck = false;
        // 监听队列
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    }
}

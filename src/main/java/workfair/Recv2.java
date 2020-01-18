//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workfair;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("test_work_queue", false, false, false, (Map)null);
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("[2]: Recv msg:" + msg);

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException var10) {
                    var10.printStackTrace();
                } finally {
                    System.out.println("[2]: done");
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };
        boolean autoAck = false;
        channel.basicConsume("test_work_queue", autoAck, consumer);
    }
}

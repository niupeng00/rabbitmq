//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP.BasicProperties;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "test_work_queue";

    public Send() {
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        boolean durable = false;
        channel.queueDeclare("test_work_queue", durable, false, false, (Map)null);
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for(int i = 0; i < 50; ++i) {
            String msg = "hello " + i;
            System.out.println("[WQ]：send：" + msg);
            channel.basicPublish("", "test_work_queue", (BasicProperties)null, msg.getBytes());
            Thread.sleep(20L);
        }

        channel.close();
        connection.close();
    }
}

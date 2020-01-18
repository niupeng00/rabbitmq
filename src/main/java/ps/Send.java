package ps;

import com.rabbitmq.client.Connection;
import util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

    }
}

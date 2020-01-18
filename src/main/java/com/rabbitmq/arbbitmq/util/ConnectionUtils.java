package com.rabbitmq.arbbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  MQ 挂掉，接受的数据都会丢失
 *  MQ 持久化，解决数据丢失问题
 *
 */
public class ConnectionUtils {

    /**
     * 获取 MQ 的连接
     * @return
     */
    public static Connection getConnection () throws IOException, TimeoutException {
        // 定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 设置服务器地址
        factory.setHost("127.0.0.1");
        // 设置端口
        factory.setPort(5672);
        // 设置 数据库
        factory.setVirtualHost("/vhost_mmr");
        // 用户名
        factory.setUsername("niupeng");
        // 密码
        factory.setPassword("niupeng");

        return factory.newConnection();

    }
}

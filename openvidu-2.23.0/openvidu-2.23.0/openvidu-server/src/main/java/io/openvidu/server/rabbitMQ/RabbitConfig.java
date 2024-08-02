package io.openvidu.server.rabbitMQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private int port;


}

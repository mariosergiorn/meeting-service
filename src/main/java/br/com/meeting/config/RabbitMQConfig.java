package br.com.meeting.config;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${meeting.exchange.direct}")
    private String exchange;

    @Value("${meeting.queue.notification}")
    private String queue;

    @Bean
    public RabbitAdmin topicRabbitAdmin(CachingConnectionFactory connectionFactory) {

        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        DirectExchange directExchange = new DirectExchange(exchange);

        admin.declareExchange(directExchange);

        Queue notificationQueue = new Queue(queue);
        Binding notificationBinding = BindingBuilder.bind(notificationQueue).to(directExchange).withQueueName();

        admin.declareQueue(notificationQueue);
        admin.declareBinding(notificationBinding);

        return admin;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

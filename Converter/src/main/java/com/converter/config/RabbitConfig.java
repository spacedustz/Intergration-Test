//package com.converter.config;
//
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Map;
//import java.util.HashMap;
//
//@Configuration
//public class RabbitConfig {
//
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//
//    @Value("${spring.rabbitmq.port}")
//    private int port;
//
//    @Value("${spring.rabbitmq.username}")
//    private String id;
//
//    @Value("${spring.rabbitmq.password}")
//    private String pw;
//
//    @Value("${spring.rabbitmq.template.exchange}")
//    private String exchange;
//
//    @Value("${spring.rabbitmq.template.default-receive-queue}")
//    private String queue;
//
//    @Value("${spring.rabbitmq.template.routing-key}")
//    private String key;
//
//    // Topic 타입의 Exchange 생성
//    @Bean
//    public Exchange exchange() { return new TopicExchange(exchange); }
//
//    // Quorum Queue 생성
//    @Bean
//    public Queue queue() {
//        // Queue 타입, Arguments 설정
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-queue-type", "quorum");
//        args.put("x-message-ttl", 200000);
//
//
//        return QueueBuilder.durable(queue).withArguments(args).build();
//    }
//
//    // Exchange <-> Queue Binding
//    @Bean
//    public Binding binding(Queue queue, Exchange exchange) {
//        Map<String, Object> args = new HashMap<>();
//        args.put("x-message-ttl", 200000);
//
//        return BindingBuilder.bind(queue).to(exchange).with(key).and(args);
//    }
//
//    // Message Converter Bean 주입
//    @Bean
//    MessageConverter converter() { return new Jackson2JsonMessageConverter(); }
//
//    // RabbitMQ와의 연결을 위한 Connection Factory Bean 생성
//    @Bean
//    public ConnectionFactory factory() {
//        CachingConnectionFactory factory = new CachingConnectionFactory();
//        factory.setHost(host);
//        factory.setPort(port);
//        factory.setUsername(id);
//        factory.setPassword(pw);
//
//        return factory.getRabbitConnectionFactory();
//    }
//
//    // Rabbit Template 생성
//    @Bean
//    RabbitTemplate template(org.springframework.amqp.rabbit.connection.ConnectionFactory factory, MessageConverter converter) {
//        RabbitTemplate template = new RabbitTemplate(factory);
//        template.setMessageConverter(converter);
//
//        return template;
//    }
//
//    // Subscribe Listener
//    @Bean
//    SimpleRabbitListenerContainerFactory listener(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
//        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(converter());
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
//
//        return factory;
//    }
//}

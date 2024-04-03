//package com.malykh.student.RabbitMQ;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class MQConfiguration {
//    static final String queueName = "firstQueue";
//    static final String exchangeName = "testExchange";
//
//    @Bean
//    public Queue myQueue() {
//        return new Queue(queueName, false);
//    }
//    @Bean
//    public Exchange exchange() {
//        return new TopicExchange(exchangeName, false, false);
//    }
//    @Bean
//    public Binding binding(Queue queue, Exchange exchange){
//        return BindingBuilder.bind(queue).to(exchange).with("first.key").noargs();
//    }
//}

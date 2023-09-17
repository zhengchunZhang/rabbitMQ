package com.atguigu.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TtlQueueConfig {
    //普通交换机的名称
    public  static  final  String X_EXCHANGE = "X";
    //死信交换机的名称
    public static final String Y_DEAD_LATTER_EXCHANGE = "Y";
    //普通队列的名称
    public  static final String QUEUE_A = "QA";
    public  static final String QUEUE_B = "QB";
    public  static final String QUEUE_C = "QC";
    //死信队列的名称
    public static final String DEAD_LETTER_QUEUE = "QD";
    //声明xEachange
    @Bean("xExchange")
    public DirectExchange XExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LATTER_EXCHANGE);
    }
    //声明队列
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATTER_EXCHANGE);
        //设置死信
        arguments.put("x-dead-letter-routing-key","YD");
        //ms单位
        arguments.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();

    }

    //声明队列
    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        arguments.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();

    }
    //声明队列
    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange",Y_DEAD_LATTER_EXCHANGE);
        //设置死信
        arguments.put("x-dead-letter-routing-key","YD");
        //ms单位
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();

    }
    //死信队列
    @Bean("queueD")
    public  Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");

    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");

    }
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,@Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");

    }
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,@Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");

    }
}

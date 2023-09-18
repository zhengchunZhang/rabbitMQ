package com.atguigu.rabbitmq.springbootrabbitmq.config.consumer;

import com.atguigu.rabbitmq.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 基于插件的延迟消息
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }

}

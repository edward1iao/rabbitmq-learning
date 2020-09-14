package com.edward1iao.rabbitmq.ps.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.edward1iao.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 消费者1接受消息
 * @author ming3
 */
public class Consumer1 {
	private static final String QUEUE_NAME = "test_queue_topic_email";
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//将队列绑定到交换机上,同时指定路由键
		channel.queueBind(QUEUE_NAME,Producer.EXCHANGE_NAME,"message.#");
		//监听队列
		channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println("接收到来自["+QUEUE_NAME+"]队列的消息："+new String(body));
			}
		});
	}
}

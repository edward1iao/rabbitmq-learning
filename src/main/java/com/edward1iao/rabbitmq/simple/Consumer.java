package com.edward1iao.rabbitmq.simple;

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
 * 消费者接受消息
 * @author ming3
 *
 */
public class Consumer {
	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
		//老版接受消息api
//		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
//		//监听队列
//		channel.basicConsume(Producer.QUEUE_NAME, true, queueingConsumer);
//		while(true){
//			System.out.println(System.currentTimeMillis()+"接收到消息："+new String(queueingConsumer.nextDelivery().getBody()));
//		}
		//新版接受消息api
		//监听队列
		channel.basicConsume(Producer.QUEUE_NAME, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				System.out.println(System.currentTimeMillis()+"接收到消息："+new String(body));
			}
		});
	}
}

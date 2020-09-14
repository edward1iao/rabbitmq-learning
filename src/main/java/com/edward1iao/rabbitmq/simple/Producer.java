package com.edward1iao.rabbitmq.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.edward1iao.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/**
 * 生产者产生消息
 * @author ming3
 *
 */
public class Producer {
	public static final String QUEUE_NAME = "test_simple_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String msg = "==========消息内容========";
		channel.basicPublish("", QUEUE_NAME, null,msg.getBytes());
		System.out.println("发送消息："+msg);
		channel.close();
		connection.close();
	}
}

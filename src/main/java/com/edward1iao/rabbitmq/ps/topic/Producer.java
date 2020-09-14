package com.edward1iao.rabbitmq.ps.topic;

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
	public static final String EXCHANGE_NAME = "test_exchange_topic";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//声明交换机，类型为topic 处理路由键，路由键可使用匹配符#、*
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		String msg = "==========消息内容========";
		//指定消息路由键
		String routingKey = "message.add";
		//发送消息
		channel.basicPublish(EXCHANGE_NAME, routingKey, null,msg.getBytes());
		System.out.println("发送消息："+msg+",路由键为"+routingKey);
		channel.close();
		connection.close();
	}
}

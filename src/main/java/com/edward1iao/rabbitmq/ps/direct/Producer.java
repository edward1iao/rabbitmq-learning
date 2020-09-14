package com.edward1iao.rabbitmq.ps.direct;

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
	public static final String EXCHANGE_NAME = "test_exchange_direct";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//声明交换机，类型为direct 处理路由键，发送消息时处理路由键
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		String msg = "==========消息内容========";
		//指定消息路由键
		String routingKey = "warning";
		//发送消息
		channel.basicPublish(EXCHANGE_NAME, routingKey, null,msg.getBytes());
		System.out.println("发送消息："+msg);
		channel.close();
		connection.close();
	}
}

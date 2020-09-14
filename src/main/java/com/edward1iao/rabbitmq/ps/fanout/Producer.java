package com.edward1iao.rabbitmq.ps.fanout;

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
	public static final String EXCHANGE_NAME = "test_exchange_fanout";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//声明交换机，类型为fanout 不处理路由键，即发送消息时不使用routingKey
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String msg = "==========消息内容========";
		//发送消息时指定交换机而不是队列
		channel.basicPublish(EXCHANGE_NAME, "test", null,msg.getBytes());
		System.out.println("发送消息："+msg);
		channel.close();
		connection.close();
	}
}

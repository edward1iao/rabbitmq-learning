package com.edward1iao.rabbitmq.simple.durable;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.edward1iao.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
/**
 * 生产者产生消息
 * @author ming3
 *
 */
public class Producer {
	public static final String QUEUE_NAME = "test_durable_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//持久化
		boolean durable = true;
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
		String msg = "==========消息内容========";
		//deliveryMode 1 (nonpersistent)deliveryMode 2 (persistent)
		//通过传入MessageProperties.PERSISTENT_TEXT_PLAIN，配置BasicProperties的deliveryMode为2，即可持久化消息
		channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
		System.out.println("发送消息："+msg);
		channel.close();
		connection.close();
	}
}

package com.edward1iao.rabbitmq.work;

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
	public static final String QUEUE_NAME = "test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for (int i = 0; i < 50; i++) {
			String msg = "==========消息内容"+i+"========";
			channel.basicPublish("", QUEUE_NAME, null,msg.getBytes());
			System.out.println(System.currentTimeMillis()+"发送消息："+msg);
			Thread.sleep(50);
		}
		channel.close();
		connection.close();
	}
}

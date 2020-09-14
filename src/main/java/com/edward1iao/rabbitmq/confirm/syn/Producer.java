package com.edward1iao.rabbitmq.confirm.syn;

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
	public static final String QUEUE_NAME = "test_confirm_syn_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//设置channel为confirm模式
		channel.confirmSelect();
		String msg = "==========消息内容========";
		channel.basicPublish("", QUEUE_NAME, null,msg.getBytes());
		System.out.println("发送内容："+msg);
		channel.basicPublish("", QUEUE_NAME, null,msg.getBytes());
		System.out.println("发送内容："+msg);
		if(channel.waitForConfirms()){
			System.out.println("发送消息成功!");
		}else{
			System.out.println("发送消息失败！");
		}
		channel.close();
		connection.close();
	}
}

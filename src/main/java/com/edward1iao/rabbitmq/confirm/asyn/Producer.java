package com.edward1iao.rabbitmq.confirm.asyn;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

import com.edward1iao.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
/**
 * 生产者产生消息
 * @author ming3
 *
 */
public class Producer {
	public static final String QUEUE_NAME = "test_confirm_asyn_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtil.getConnection();
		//从连接中获得一个通道
		Channel channel = connection.createChannel();
		//创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		//设置channel为confirm模式
		channel.confirmSelect();
		final SortedSet<Long> unConfirm = Collections.synchronizedSortedSet(new TreeSet<Long>());
		//添加监听消息确认回调
		channel.addConfirmListener(new ConfirmListener() {
			
			/**
			 * 消息接受失败
			 */
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				if(multiple){
					System.out.println("deliveryTag:"+deliveryTag+" handleNack multiple");
					unConfirm.headSet(deliveryTag+1).clear();
				}else{
					System.out.println("deliveryTag:"+deliveryTag+" handleNack");
					unConfirm.remove(deliveryTag);
				}
			}
			/**
			 * 消息接受成功
			 */
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				if(multiple){
					System.out.println("deliveryTag:"+deliveryTag+" handleAck multiple");
					unConfirm.headSet(deliveryTag+1).clear();
				}else{
					System.out.println("deliveryTag:"+deliveryTag+" handleAck");
					unConfirm.remove(deliveryTag);
				}
			}
		});
		for(int i=0;i<50;i++){
			String msg = "==========消息内容========"+i;
			unConfirm.add(channel.getNextPublishSeqNo());//添加消息序号
			channel.basicPublish("", QUEUE_NAME, null,msg.getBytes());
			System.out.println("发送内容："+msg);
		}
		Thread.sleep(10000);
		System.out.println("unConfirm:"+unConfirm);
		channel.close();
		connection.close();
	}
}

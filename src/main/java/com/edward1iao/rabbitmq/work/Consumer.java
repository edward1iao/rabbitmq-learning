package com.edward1iao.rabbitmq.work;

import java.io.IOException;

import com.edward1iao.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消费者接受消息
 * @author ming3
 *
 */
public class Consumer{
	public static void main(String[] args) {
		Consumer.threadStart(1,500);
		Consumer.threadStart(2,200);
	}
	
	private int num;//编号
	
	private boolean isFair = true;//是否公平分发，true是，false为轮询分发
	
	private long sleepTime;//接受完消息之后休眠时间
	
	private int handleCount = 0;//已处理消息条目
	
	public Consumer(int num,long sleepTime) {
		super();
		this.num = num;
		this.sleepTime = sleepTime;
	}
	
	public void run() {
		System.out.println("[消费者"+num+"]启动");
		try {
			Connection connection = ConnectionUtil.getConnection();
			//从连接中获得一个通道
			final Channel channel = connection.createChannel();
			//创建队列声明
			channel.queueDeclare(Producer.QUEUE_NAME, false, false, false, null);
			
			//每次获取数据条目
			if(isFair){
				int prefetchCount = 1;
				channel.basicQos(prefetchCount);
			}
			//是否自动应答
			final boolean autoAck = !isFair;
			
			//监听队列
			channel.basicConsume(Producer.QUEUE_NAME, autoAck, new DefaultConsumer(channel){
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope,
						BasicProperties properties, byte[] body) throws IOException {
					System.out.println("[消费者"+num+"]接收到消息："+new String(body));
					//处理业务耗时
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						if(!autoAck)channel.basicAck(envelope.getDeliveryTag(), false);
						handleCount++;
						System.out.println("[消费者"+num+"]业务处理完毕,已处理条目:"+handleCount);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 线程启动
	 * @param num 消费者编号
	 * @param sleepTime 获取消息后休眠时间（模拟性能差）
	 */
	public static void threadStart(final int num,final long sleepTime){
		new Thread(new Runnable() {
			public void run() {
				new Consumer(num,sleepTime).run();
			}
		}).start();
	}
}

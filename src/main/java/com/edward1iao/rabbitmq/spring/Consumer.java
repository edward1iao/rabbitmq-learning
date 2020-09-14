package com.edward1iao.rabbitmq.spring;

public class Consumer {
	
	public void listen(String content){
		System.out.println("消费者接收消息："+content);
	}
	
//	public void listen(Object object){
//		System.out.println("消费者接收对象消息："+object.toString());
//	}
}

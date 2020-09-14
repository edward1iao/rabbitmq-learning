package com.edward1iao.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring使用rabbit操作
 * @author ming3
 *
 */
public class SpringRabbitMain {
	public static void main(String[] args) throws InterruptedException {
		@SuppressWarnings("resource")
		AbstractApplicationContext abstractApplicationContext = new ClassPathXmlApplicationContext("classpath:context.xml");
		//获取rabbitmq模板，实际项目中可以用注入的形式
		RabbitTemplate rabbitTemplate = abstractApplicationContext.getBean(RabbitTemplate.class);
		//发送消息
		rabbitTemplate.convertAndSend("hello world!");
//		rabbitTemplate.convertAndSend(new Test("test", 10788L, new Date(System.currentTimeMillis())));
		//休眠1秒
		Thread.sleep(1000);
		//容器销毁
		abstractApplicationContext.destroy();
	}
}

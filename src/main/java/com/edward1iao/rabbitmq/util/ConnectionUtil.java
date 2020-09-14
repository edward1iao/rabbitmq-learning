package com.edward1iao.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	private final static String HOST = "188.131.235.133";
	private final static int PORT = 5672;
	private final static String VIRTUALHOST = "/vhost_lxy";
	private final static String USERNAME = "user_lxy";
	private final static String PASSWORD = "a123456";
	
	
	public static Connection getConnection() throws IOException, TimeoutException{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost(HOST);
		connectionFactory.setPort(PORT);
		connectionFactory.setVirtualHost(VIRTUALHOST);
		connectionFactory.setUsername(USERNAME);
		connectionFactory.setPassword(PASSWORD);
		return connectionFactory.newConnection();
	}
}

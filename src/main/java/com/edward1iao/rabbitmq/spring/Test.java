package com.edward1iao.rabbitmq.spring;

import java.io.Serializable;
import java.util.Date;

public class Test implements Serializable{
	private static final long serialVersionUID = 1L;
	private String str;
	private Long number;
	private Date date;
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Test(String str, Long number, Date date) {
		super();
		this.str = str;
		this.number = number;
		this.date = date;
	}
	public Test() {
		super();
	}
	@Override
	public String toString() {
		return "Test [str=" + str + ", number=" + number + ", date=" + date
				+ "]";
	}
}

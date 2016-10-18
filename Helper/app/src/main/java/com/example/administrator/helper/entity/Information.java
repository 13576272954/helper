package com.example.administrator.helper.entity;

import java.sql.Timestamp;

/**
 * 实体类
 * 消息
 * @author Administrator
 *
 */
public class Information {
	private Integer id;//消息id
	private User sendUser;//发送用户
	private User reveiveUser;//接收用户
	private String value;//消息内容
	private Timestamp sendTime;//发送时间
	
	
	public Information() {
		super();
	}
	
	
	public Information(Integer id, User sendUser, User reveiveUser, String value, Timestamp sendTime) {
		super();
		this.id = id;
		this.sendUser = sendUser;
		this.reveiveUser = reveiveUser;
		this.value = value;
		this.sendTime = sendTime;
	}
	

	public Information(User sendUser, User reveiveUser, String value, Timestamp sendTime) {
		super();
		this.sendUser = sendUser;
		this.reveiveUser = reveiveUser;
		this.value = value;
		this.sendTime = sendTime;
	}


	public User getSendUser() {
		return sendUser;
	}
	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}
	public User getReveiveUser() {
		return reveiveUser;
	}
	public void setReveiveUser(User reveiveUser) {
		this.reveiveUser = reveiveUser;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public Integer getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Information [id=" + id + ", sendUser=" + sendUser + ", reveiveUser=" + reveiveUser + ", value=" + value
				+ ", sendTime=" + sendTime + "]";
	}
	
	
	
}

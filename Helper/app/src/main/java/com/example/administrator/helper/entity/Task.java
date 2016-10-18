package com.example.administrator.helper.entity;

import java.sql.Timestamp;

/**
 * 实体类
 * 任务
 * @author Administrator
 *
 */
public class Task {
	private Integer id;//任务id
	private User sendUser;//发布者
	private Timestamp beginTime;//开始时间
	private Timestamp latestTime;//限定最后时间
	private String makePlace;//任务地点
	private String submitPlace;//提交地点  
	private String phone;//联系电话
	private TaskType taskType;//任务类型
	private String taskDemand;//任务要求
	private Integer money;//任务赏金
	private Integer taskStatus;//任务状态
	//1、未开始2、进行中3、已完成
	public Task() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Task(User sendUser, Timestamp beginTime, Timestamp latestTime, String makePlace, String submitPlace, String phone,
			TaskType taskType, String taskDemand, Integer money, Integer taskStatus) {
		super();
		this.sendUser = sendUser;
		this.beginTime = beginTime;
		this.latestTime = latestTime;
		this.makePlace = makePlace;
		this.submitPlace = submitPlace;
		this.phone = phone;
		this.taskType = taskType;
		this.taskDemand = taskDemand;
		this.money = money;
		this.taskStatus = taskStatus;
	}



	public Task(Integer id, User sendUser, Timestamp beginTime, Timestamp latestTime, String makePlace, String submitPlace,
			String phone, TaskType taskType, String taskDemand, Integer money, Integer taskStatus) {
		super();
		this.id = id;
		this.sendUser = sendUser;
		this.beginTime = beginTime;
		this.latestTime = latestTime;
		this.makePlace = makePlace;
		this.submitPlace = submitPlace;
		this.phone = phone;
		this.taskType = taskType;
		this.taskDemand = taskDemand;
		this.money = money;
		this.taskStatus = taskStatus;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public User getSendUser() {
		return sendUser;
	}

	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(Timestamp latestTime) {
		this.latestTime = latestTime;
	}

	public String getMakePlace() {
		return makePlace;
	}

	public void setMakePlace(String makePlace) {
		this.makePlace = makePlace;
	}

	public String getSubmitPlace() {
		return submitPlace;
	}

	public void setSubmitPlace(String submitPlace) {
		this.submitPlace = submitPlace;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public String getTaskDemand() {
		return taskDemand;
	}

	public void setTaskDemand(String taskDemand) {
		this.taskDemand = taskDemand;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", sendUser=" + sendUser + ", beginTime=" + beginTime + ", latestTime=" + latestTime
				+ ", makePlace=" + makePlace + ", submitPlace=" + submitPlace + ", phone=" + phone + ", taskType="
				+ taskType + ", taskDemand=" + taskDemand + ", money=" + money + ", taskStatus=" + taskStatus + "]";
	}
	
}

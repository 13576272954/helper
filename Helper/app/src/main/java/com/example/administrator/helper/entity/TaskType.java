package com.example.administrator.helper.entity;

public class TaskType {
	private Integer id;
	private String taskType;
	public TaskType(Integer id, String taskType) {
		super();
		this.id = id;
		this.taskType = taskType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	@Override
	public String toString() {
		return "TaskType [id=" + id + ", taskType=" + taskType + "]";
	}
	

}

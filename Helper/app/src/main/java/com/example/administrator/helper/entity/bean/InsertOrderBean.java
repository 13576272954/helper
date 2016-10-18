package com.example.administrator.helper.entity.bean;

public class InsertOrderBean {
	private Integer taskId;//任务id
	private Integer couponId;//优惠券id
	private Double price;//订单价格
	public InsertOrderBean( Integer taskId, Integer couponId, Double price) {
		super();
		this.taskId = taskId;
		this.couponId = couponId;
		this.price = price;
	}
	
	
	public InsertOrderBean(Integer couponId, Double price) {
		super();
		this.couponId = couponId;
		this.price = price;
	}


	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}

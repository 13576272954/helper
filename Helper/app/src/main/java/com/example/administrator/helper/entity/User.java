package com.example.administrator.helper.entity;

import java.sql.Timestamp;

/**
 * 实体类
 * 用户
 * @author Administrator
 *
 */
public class User {
	private Integer id;//用户id
	private String name;//用户账号
	private String password;//用户密码
	private Integer payPassword;//支付密码
	private String sex;//性别
	private Integer age;//年龄
	private Integer credit;//用户信誉
	private String rank;//信誉等级
	private Integer points;//积分
	private String school;//学校
	private String signName;//签名
	private Double balance;//余额
	private Timestamp createTime;//创建日期
	private String phoneNumber;//电话号码
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	

	public User(String name, String password, Integer payPassword,  String sex, Integer age,
			Integer credit, String rank, Integer points, String school, String signName, Double balance,
			Timestamp createTime, String phoneNumber) {
		super();
		this.name = name;
		this.password = password;
		this.payPassword = payPassword;
		this.sex = sex;
		this.age = age;
		this.credit = credit;
		this.rank = rank;
		this.points = points;
		this.school = school;
		this.signName = signName;
		this.balance = balance;
		this.createTime = createTime;
		this.phoneNumber = phoneNumber;
	}


	public User(Integer id, String name, String password, Integer payPassword,  String sex, Integer age,
			Integer credit, String rank, Integer points, String school, String signName, Double balance,
			Timestamp createTime, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.payPassword = payPassword;
		this.sex = sex;
		this.age = age;
		this.credit = credit;
		this.rank = rank;
		this.points = points;
		this.school = school;
		this.signName = signName;
		this.balance = balance;
		this.createTime = createTime;
		this.phoneNumber = phoneNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getPayPassword() {
		return payPassword;
	}


	public void setPayPassword(Integer payPassword) {
		this.payPassword = payPassword;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public Integer getCredit() {
		return credit;
	}


	public void setCredit(Integer credit) {
		this.credit = credit;
	}


	public Integer getPoints() {
		return points;
	}


	public void setPoints(Integer points) {
		this.points = points;
	}


	public String getSchool() {
		return school;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public String getSignName() {
		return signName;
	}


	public void setSignName(String signName) {
		this.signName = signName;
	}


	public Double getBalance() {
		return balance;
	}


	public void setBalance(Double balance) {
		this.balance = balance;
	}


	public Timestamp getCreateDate() {
		return createTime;
	}


	public void setCreateDate(Timestamp createDate) {
		this.createTime = createDate;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public Integer getId() {
		return id;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", payPassword=" + payPassword
				+ ", sex=" + sex + ", age=" + age + ", credit=" + credit + ", rank=" + rank + ", points=" + points
				+ ", school=" + school + ", signName=" + signName + ", balance=" + balance + ", createTime="
				+ createTime + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
	
}

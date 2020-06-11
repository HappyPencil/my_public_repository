package com.zone.bean;

import java.math.BigDecimal;

public class User implements BaseBean{

	private Integer id;
	private String name;
	private String password;
	private BigDecimal count;
	
	
	public User(Integer id, String name, String password, BigDecimal count) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.count = count;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", password=" + password + ", count=" + count + "]";
	}
	public void setId(Integer id) {
		this.id = id;
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
	public BigDecimal getCount() {
		return count;
	}
	public void setCount(BigDecimal count) {
		this.count = count;
	}

}

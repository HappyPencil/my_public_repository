package com.zone.bean;

public class Admin implements BaseBean{

	private Integer id;
	private String password;
	@Override
	public String toString() {
		return "Admin [id=" + id + ", password=" + password + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

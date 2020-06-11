package com.zone.bean;

public class Cart implements BaseBean {

	public Cart() {
	}
	
	private Integer id;
	private Integer user;
	private Integer product;
	private Integer count;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getProduct() {
		return product;
	}
	public void setProduct(Integer product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Cart(Integer id, Integer user, Integer product, Integer count) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.count = count;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", user=" + user + ", product=" + product + ", count=" + count + "]";
	}
	

}

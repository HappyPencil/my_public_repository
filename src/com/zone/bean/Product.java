package com.zone.bean;

import java.math.BigDecimal;

public class Product implements BaseBean{

	private Integer id;
	private String name;
	private BigDecimal price;
	private Integer count;
	private String detail;
	
	public Product() {
		super();
	}
	
	
	public Product(Integer id, String name, BigDecimal price, Integer count, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.detail = detail;
	}


	@Override
	public String toString() {
		return "product [id=" + id + ", name=" + name + ", price=" + price + ", count=" + count + ", detail=" + detail
				+ "]";
	}
	public Integer getId() {
		return id;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

}

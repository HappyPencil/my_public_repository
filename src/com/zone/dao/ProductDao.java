package com.zone.dao;


import java.util.List;

import com.zone.bean.PageBean;
import com.zone.bean.Product;

public interface ProductDao extends BaseDao{

	Integer createProduct(Product product);
	
	void updateProduct(Product product);
	
	void addToCart(Integer user_id, Integer product_id, Integer product_count);
	
}

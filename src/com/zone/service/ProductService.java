package com.zone.service;

import com.zone.bean.PageBean;
import com.zone.bean.Product;

public interface ProductService extends BaseService {

	PageBean selectProducts(Class beanClass,Integer recordCountPerPage,Integer pageNumber);
	
	void addToCart(Integer user_id,Integer product_id, Integer product_count);

	
}

package com.zone.serviceImp;

import com.zone.bean.PageBean;
import com.zone.dao.DaoFactory;
import com.zone.dao.PageBeanDao;
import com.zone.dao.ProductDao;
import com.zone.service.ProductService;

public class ProductServiceImp implements ProductService {

	public ProductServiceImp() {
	}

	public PageBean selectProducts(Class beanClass,Integer recordCount,Integer pageNumber) {
		
		//TypeParameter[] typeParameter = (TypeParameter[]) beanClass.getTypeParameters();
		
		@SuppressWarnings("unchecked")
		PageBeanDao daoImp = (PageBeanDao) DaoFactory.getFactory()
				.getDaoImp(PageBeanDao.class);
		PageBean productPageBean = daoImp.getPageBean(beanClass, recordCount, pageNumber);
		
		return productPageBean;
	}
	
	public void addToCart(Integer user_id,Integer product_id, Integer product_count) {
		ProductDao daoImp = (ProductDao) DaoFactory.getFactory().getDaoImp(ProductDao.class);
		daoImp.addToCart(user_id,product_id,product_count);
	}



}

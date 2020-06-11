package com.zone.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.zone.bean.Cart;
import com.zone.bean.PageBean;
import com.zone.bean.Product;
import com.zone.dao.ProductDao;
import com.zone.jdbc.JdbcTools;

public class ProductDaoImp implements ProductDao {

	public ProductDaoImp() {
	}

	public Integer createProduct(Product product) {
		String sql="insert into product (product_name,product_price,product_count,product_detail) value(?,?,?,?)";
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = null;
		ResultSet resultset = null; 
		try {
			preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setBigDecimal(2, product.getPrice());
			preparedStatement.setInt(3, product.getCount());
			preparedStatement.setString(4, product.getDetail());
			preparedStatement.executeUpdate();
			resultset = preparedStatement.getGeneratedKeys();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer id = null;
		try {
			while(resultset.next()){
				id = resultset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcTools.release(connection, preparedStatement, resultset);
		return id;
		
	}

	public List<Product> selectProductsByName(String name) {
		List<Product> products = null;
		String sql = "select * from product where product_name="+name;
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement  preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		products = JdbcTools.ResultsetToList(resultSet, Product.class);
		JdbcTools.release(connection, preparedStatement, resultSet);
		return products;
	}
	
	public Cart selectCartByUser(Integer user_id) {
		String sql = "select * from cart where id="+user_id;
		Connection connection = JdbcTools.getConnection(JdbcTools.getProperty());
		PreparedStatement preparedStatement = JdbcTools.getPrepareStatement(connection, sql);
		ResultSet resultSet = JdbcTools.getResultSet(preparedStatement);
		List<Cart> cart = JdbcTools.ResultsetToList(resultSet, Cart.class);
		JdbcTools.release(connection, preparedStatement, resultSet);
		if (cart.size()>0) {
			return cart.remove(0);
		}
		return null;
	}

	public void addToCart(Integer user_id, Integer product_id, Integer product_count) {
		Cart cart = this.selectCartByUser(user_id);
		if (cart!=null) {
			String sql = "update cart set cart_id="+cart.getId()+",user_id="+user_id+",product_id="+product_id+",product_count="+product_count;
			JdbcTools.callUpdate(sql);
		}else {
			String sql = "insert into cart values(null,"+user_id+","+product_id+","+product_count+")";
			JdbcTools.callUpdate(sql);
		}
		
	}

	public void updateProduct(Product product) {
		String sql = "update product set product_id="+product.getId()
				+",product_name="+product.getName()
				+",product_price="+product.getPrice()
				+",product_count="+product.getCount()
				+",product_detail="+product.getDetail();
		JdbcTools.callUpdate(sql);
	}
	
}

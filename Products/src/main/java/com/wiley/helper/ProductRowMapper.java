package com.wiley.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wiley.bean.Product;

public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product product = new Product();
		product.setId(rs.getLong("ID"));
		product.setCode(rs.getString("CODE"));
		product.setDescription(rs.getString("DESCRIPTION"));
		product.setName(rs.getString("NAME"));
		product.setPrice(rs.getDouble("PRICE"));
		return product;
	}
	

}

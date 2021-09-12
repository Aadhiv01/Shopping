package com.wiley.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.wiley.bean.Product;
import com.wiley.helper.ProductRowMapper;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Product> findAll() {
		List<Product> products = jdbcTemplate.query("SELECT * FROM PRODUCT", new ProductRowMapper());
		return products;
	}

	@Override
	public Optional<Product> findByCode(String code) {
		Optional<Product> product = Optional.of(jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE CODE = ?", new ProductRowMapper(),code));
		return product;
	}

}

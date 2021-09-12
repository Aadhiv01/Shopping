package com.wiley.persistence;

import java.util.List;
import java.util.Optional;


import com.wiley.bean.Product;


public interface ProductDao{
	public	List<Product> findAll();
	public Optional<Product> findByCode(String code);
}

package com.wiley.service;

import java.util.List;
import java.util.Optional;

import com.wiley.bean.Product;

public interface ProductService {
	public List<Product> getAllProducts();
	public Optional<Product> getProductByCode(String code);
}

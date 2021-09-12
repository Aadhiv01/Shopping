package com.wiley.resource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.bean.Product;
import com.wiley.bean.ProductList;
import com.wiley.service.ProductService;

@RestController
public class ProductResource {

	@Autowired
	ProductService productService;
	
	@GetMapping(path = "/products",produces = "Application/json")
	ProductList getAllProducts(){
		return new ProductList(productService.getAllProducts());
	}
	
	@GetMapping(path = "/products/code/{code}",produces = "Application/json")
	ResponseEntity<Optional<Product>> getProductByCode(@PathVariable("code") String code){
		if(code == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		try {
			Optional<Product> product = productService.getProductByCode(code);
			if(product.isPresent())
				return ResponseEntity.status(HttpStatus.OK).body(product);
		}catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}

	package com.wiley.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.InventoryList;
import com.wiley.bean.OrderItem;
import com.wiley.bean.Orders;
import com.wiley.bean.Product;
import com.wiley.bean.ProductList;
import com.wiley.bean.UserBean;
import com.wiley.persistence.OrderDao;
import com.wiley.persistence.OrderItemDao;
import com.wiley.persistence.UserDao;

@Component("service")
public class ShoppingServiceImpl implements ShoppingService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderItemDao orderItemDao;
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public boolean checkUser(String email, boolean isNew) {
		UserBean user = userDao.findByEmail(email);
		if(isNew && user.getName() != null)
			return false;
		else if(!isNew && user.getName() == null)
			return false;
		return true;
	}
	
	@Override
	public boolean insertUser(UserBean user){
		return userDao.save(user) != null;
	}
	
	@Override
	public UserBean fetchUser(String email){
		return userDao.findByEmail(email);
	}

	@Override
	public List<Product> getAllProducts() {
		ProductList productList = restTemplate.getForObject("http://localhost:8082/products", ProductList.class);
		List<Product> products = productList.getProducts();
		return products;
	}
	
	@Override
	public List<InventoryItem> getAllItems(){
		InventoryList inventoryList = restTemplate.getForObject("http://localhost:8084/inventories", InventoryList.class);
		List<InventoryItem> items = inventoryList.getItems();
		return items;
	}
	
	@Override
	public Orders createOrder(Orders orders) {
		ProductList productList = restTemplate.getForObject("http://localhost:8082/products", ProductList.class);
		List<Product> products = productList.getProducts();
		InventoryList inventoryList = restTemplate.getForObject("http://localhost:8084/inventories", InventoryList.class);
		List<InventoryItem> items = inventoryList.getItems();
		for(OrderItem order : orders.getItems()) {
			Optional<Product> product = products.stream().filter(e->e.getId()==order.getProductId()).findAny();
			if(product.isEmpty()) {
				System.out.println("Product Empty");
				return null;
			}
			System.out.println(product.get());
			Optional<InventoryItem> item = items.stream().filter(e->e.getProductCode().equals(product.get().getCode())).findAny();
			if(item.isEmpty() || order.getQuantity() > item.get().getAvailableQuantity()) {
				System.out.println(item.isEmpty() + "Item Empty");
				return null;
			}
			order.setProductPrice(new BigDecimal(product.get().getPrice()));
		}
		orders.getItems().forEach(e->orderItemDao.save(e));
		//restTemplate.exchange("http://localhost:8084/inventories", null, null, null, null);
		return orderDao.save(orders);
	}
	
}
package com.wiley.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiley.bean.InventoryItem;
import com.wiley.persistence.InventoryDao;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryDao inventoryDao;
	
	@Override
	public InventoryItem findInventoryByProductCode(String code) {
		return inventoryDao.findInventoryByProductCode(code);
	}

	@Override
	public List<InventoryItem> getInventory() {
		return inventoryDao.findAll();
	}

}

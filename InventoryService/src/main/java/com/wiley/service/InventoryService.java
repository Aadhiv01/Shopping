package com.wiley.service;

import java.util.List;

import com.wiley.bean.InventoryItem;

public interface InventoryService {
	InventoryItem findInventoryByProductCode(String code);
	List<InventoryItem> getInventory();
}

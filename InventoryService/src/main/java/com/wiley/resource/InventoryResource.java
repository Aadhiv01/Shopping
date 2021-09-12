package com.wiley.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.bean.InventoryItem;
import com.wiley.bean.InventoryList;
import com.wiley.service.InventoryService;


@RestController
public class InventoryResource {

	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping(path = "/inventories",produces = "Application/json")
	InventoryList getInventory(){
		return new InventoryList(inventoryService.getInventory());
	}
	
	@GetMapping(path="/inventories/code/{code}",produces = "Application/json")
	ResponseEntity<InventoryItem> getInventoryByProductCode(@PathVariable("code") String code) {
		if(code == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		InventoryItem item = inventoryService.findInventoryByProductCode(code);
		if(item != null)
			return ResponseEntity.status(HttpStatus.OK).body(item);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}

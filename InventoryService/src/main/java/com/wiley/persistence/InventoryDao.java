package com.wiley.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiley.bean.InventoryItem;

@Repository
public interface InventoryDao extends JpaRepository<InventoryItem, Long>{
	InventoryItem findInventoryByProductCode(String productCode);
}

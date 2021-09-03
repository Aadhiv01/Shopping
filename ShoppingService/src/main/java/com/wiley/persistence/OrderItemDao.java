package com.wiley.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiley.bean.OrderItem;

@Repository
public interface OrderItemDao  extends JpaRepository<OrderItem,Long>{

}

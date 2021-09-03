package com.wiley.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiley.bean.Orders;

@Repository
public interface OrderDao extends JpaRepository<Orders, Long>{
}

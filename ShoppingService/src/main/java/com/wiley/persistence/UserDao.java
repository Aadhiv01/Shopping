package com.wiley.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiley.bean.UserBean;

public interface UserDao extends JpaRepository<UserBean, String>{
	UserBean findByEmail(String email);
}

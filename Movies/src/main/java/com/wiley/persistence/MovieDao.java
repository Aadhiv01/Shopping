package com.wiley.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiley.bean.MovieBean;

@Repository
public interface MovieDao extends JpaRepository<MovieBean, Integer>{

}

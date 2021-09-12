package com.wiley.persistence;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wiley.bean.Rating;

@Repository
public interface RatingDao extends JpaRepository<Rating, Integer>{
	Collection<Rating> findByUserId(int id);
	Collection<Rating> findByMovieId(int id);
}

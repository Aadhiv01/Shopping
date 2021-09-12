package com.wiley.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wiley.bean.Rating;
import com.wiley.persistence.RatingDao;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	RatingDao ratingDao;
	
	@Override
	public Collection<Rating> getAllRatings() {
		return ratingDao.findAll();
	}

	@Override
	public Collection<Rating> getRatingsByUserId(int id) {
		return ratingDao.findByUserId(id);
	}

	@Override
	public void insertRating(Rating rating) {
		ratingDao.save(rating);
	}

	@Override
	public Collection<Rating> getAverageRatingsByMovieId(int id) {
		return ratingDao.findByMovieId(id);
	}

}

package com.wiley.service;

import java.util.Collection;

import com.wiley.bean.Rating;

public interface RatingService {
	public Collection<Rating> getAllRatings();
	public Collection<Rating> getRatingsByUserId(int id);
	public Collection<Rating> getAverageRatingsByMovieId(int id);
	public void insertRating(Rating rating);
}

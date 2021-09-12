package com.wiley.resource;

import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.bean.Rating;
import com.wiley.bean.RatingParent;
import com.wiley.bean.Review;
import com.wiley.service.RatingService;

@RestController
public class RatingController {

	@Autowired
	RatingService ratingService;

	@CrossOrigin
	@GetMapping(path = "/ratings",produces = "Application/json")
	Collection<Rating> findAllRatings(){
		return ratingService.getAllRatings();
	}
	
	@CrossOrigin
	@GetMapping(path="/ratings/{uid}",produces = "Application/json")
	RatingParent getRatingById(@PathVariable("uid") int id) {
		RatingParent ratings = new RatingParent();
		ratings.setRatings(ratingService.getRatingsByUserId(id));
		return ratings;
	}
	
	@CrossOrigin
	@GetMapping(path="/averageRatings/{mid}",produces = "Application/json")
	Review getAverageRatingByMovieId(@PathVariable("mid") int id) {
		int size=ratingService.getAverageRatingsByMovieId(id).size();
		double average = (ratingService.getAverageRatingsByMovieId(id).stream().mapToInt(Rating::getValue).sum())/1.0/(size);
		System.out.println(average);
		return new Review (new DecimalFormat("0.0").format(average), size);
	}
	
	@CrossOrigin
	@PostMapping(path="/ratings",consumes = "Application/json")
	void insertRating(@RequestBody Rating rating) {
		ratingService.insertRating(rating);
	}
	
}

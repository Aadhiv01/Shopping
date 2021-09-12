package com.wiley.resource;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.bean.MovieBean;
import com.wiley.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@CrossOrigin
	@GetMapping(path = "/movies",produces = "Application/json")
	Collection<MovieBean> findAllMovies(){
		return movieService.getAllMovies();
	}
	
	@CrossOrigin
	@GetMapping(path="/movies/{mid}",produces = "Application/json")
	MovieBean getMovieById(@PathVariable("mid") int id) {
		return movieService.getMovieById(id);
	}
	
	@CrossOrigin
	@PostMapping(path="/movies", consumes = "Application/json")
	void insertMovie(@RequestBody MovieBean movie) {
		movieService.insertMovie(movie);
	}
	
	@CrossOrigin
	@DeleteMapping(path="/movies/{mid}",produces = "Application/json")
	Collection<MovieBean> deleteMovieById(@PathVariable("mid") int id) {
		return movieService.deleteMovieById(id);
	}
}

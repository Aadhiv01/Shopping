package com.wiley.service;

import java.util.Collection;

import com.wiley.bean.MovieBean;

public interface MovieService {

	public MovieBean getMovieById(int id);
	public Collection<MovieBean> getAllMovies();
	public MovieBean insertMovie(MovieBean movie);
	public Collection<MovieBean> deleteMovieById(int id);
}

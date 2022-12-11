package com.learn.reactive.project.model;

import java.util.List;

public class Movie {

	 private MovieInfo movieInfo;
	 private List<Review> reviewList;
	 private Revenue revenue;

	 public Movie(MovieInfo movieInfo, List<Review> reviewList) {
	        this.movieInfo = movieInfo;
	        this.reviewList = reviewList;
	 }

	public MovieInfo getMovieInfo() {
		return movieInfo;
	}

	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}

	public List<Review> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<Review> reviewList) {
		this.reviewList = reviewList;
	}

	public Revenue getRevenue() {
		return revenue;
	}

	public void setRevenue(Revenue revenue) {
		this.revenue = revenue;
	}

	@Override
	public String toString() {
		return "Movie [movieInfo=" + movieInfo + ", reviewList=" + reviewList + ", revenue=" + revenue + "]";
	}
	 
}

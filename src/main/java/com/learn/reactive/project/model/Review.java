package com.learn.reactive.project.model;

public class Review {

	private Long reviewId;
    private Long movieInfoId;
    private String comment;
    private Double rating;
	public Long getReviewId() {
		return reviewId;
	}
	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}
	public Long getMovieInfoId() {
		return movieInfoId;
	}
	public void setMovieInfoId(Long movieInfoId) {
		this.movieInfoId = movieInfoId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public Review(Long reviewId, Long movieInfoId, String comment, Double rating) {
		super();
		this.reviewId = reviewId;
		this.movieInfoId = movieInfoId;
		this.comment = comment;
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", movieInfoId=" + movieInfoId + ", comment=" + comment + ", rating="
				+ rating + "]";
	}
    
    
}

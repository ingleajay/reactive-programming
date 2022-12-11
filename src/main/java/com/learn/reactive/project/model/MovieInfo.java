package com.learn.reactive.project.model;

import java.time.LocalDate;
import java.util.List;

public class MovieInfo {

	private Long movieInfoId;
    private String name;
    private Integer year;
    private List<String> cast;
    private LocalDate release_date;
    
	public Long getMovieInfoId() {
		return movieInfoId;
	}
	public void setMovieInfoId(Long movieInfoId) {
		this.movieInfoId = movieInfoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public List<String> getCast() {
		return cast;
	}
	public void setCast(List<String> cast) {
		this.cast = cast;
	}
	public LocalDate getRelease_date() {
		return release_date;
	}
	public void setRelease_date(LocalDate release_date) {
		this.release_date = release_date;
	}
	public MovieInfo(Long movieInfoId, String name, Integer year, List<String> cast, LocalDate release_date) {
		super();
		this.movieInfoId = movieInfoId;
		this.name = name;
		this.year = year;
		this.cast = cast;
		this.release_date = release_date;
	}
	@Override
	public String toString() {
		return "MovieInfo [movieInfoId=" + movieInfoId + ", name=" + name + ", year=" + year + ", cast=" + cast
				+ ", release_date=" + release_date + "]";
	}
    
    
}

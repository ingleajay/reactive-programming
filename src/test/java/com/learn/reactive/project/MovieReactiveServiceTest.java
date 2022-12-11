package com.learn.reactive.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.learn.reactive.project.services.MovieInfoService;
import com.learn.reactive.project.services.MovieReactiveService;
import com.learn.reactive.project.services.RevenueService;
import com.learn.reactive.project.services.ReviewService;

import reactor.test.StepVerifier;

public class MovieReactiveServiceTest {

	public MovieInfoService movieInfoService = new MovieInfoService();
	public ReviewService reviewService = new ReviewService();
	public RevenueService revenueService = new RevenueService();
	public MovieReactiveService movieReactiveService = 
			new MovieReactiveService(movieInfoService, reviewService,revenueService);
	
	
	@Test
	void getAllMovies() {
		var moviesInfo = movieReactiveService.getAllMovies();

        //then
        StepVerifier.create(moviesInfo)
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);

                })
                .assertNext(movieInfo -> {
                    assertEquals("The Dark Knight", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
                .verifyComplete();
	}
	
	@Test
	void getAllMovieById() {
		long movieId = 100L;
		var moviesInfo = movieReactiveService.getMovieById(movieId);

        //then
        StepVerifier.create(moviesInfo)
                
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                })
        .verifyComplete();
	}
	
	@Test
	void getAllMovieId_withrevenu() {
		long movieId = 100L;
		var moviesInfo = movieReactiveService.getMovieById_withRevenue(movieId);

        //then
        StepVerifier.create(moviesInfo)
                
                .assertNext(movieInfo -> {
                    assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
                    assertEquals(movieInfo.getReviewList().size(), 2);
                    assertNotNull(movieInfo.getRevenue());
                })
        .verifyComplete();
	}
}

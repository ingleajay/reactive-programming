package com.learn.reactive.project;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.management.RuntimeErrorException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learn.reactive.project.exception.MovieException;
import com.learn.reactive.project.services.MovieInfoService;
import com.learn.reactive.project.services.MovieReactiveService;
import com.learn.reactive.project.services.ReviewService;

import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class MovieReactiveServiceMockitoTest {

	@InjectMocks
	MovieReactiveService movieReactiveService;
	
	@Mock
	MovieInfoService movieInfoService;
	
	@Mock
	ReviewService reviewService;
	
	
	@Test
	void getAllMovies() {
		
		Mockito.when(movieInfoService.retrieveMoviesFlux())
			.thenCallRealMethod();
		
		Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
		.thenCallRealMethod();
		
		
		var movieFlux = movieReactiveService.getAllMovies();
		
		StepVerifier.create(movieFlux)
			.expectNextCount(3)
			.verifyComplete();
	}
	
	@Test
	void getAllMovies_exception() {
		
		var message = "Exception occurs in review service";
		
		Mockito.when(movieInfoService.retrieveMoviesFlux())
			.thenCallRealMethod();
		
		Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
		.thenThrow(new RuntimeException(message));
		
		
		var movieFlux = movieReactiveService.getAllMovies();
		
		StepVerifier.create(movieFlux)
			.expectError(MovieException.class)
			.verify();
	}
	
	@Test
	void getAllMovies_exception_retry() {
		
		var message = "Exception occurs in review service";
		
		Mockito.when(movieInfoService.retrieveMoviesFlux())
			.thenCallRealMethod();
		
		Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
		.thenThrow(new RuntimeException(message));
		
		
		var movieFlux = movieReactiveService.getAllMovies_retry();
		
		StepVerifier.create(movieFlux)
			.expectError(MovieException.class)
			.verify();
		
		verify(reviewService,times(4)).retrieveReviewsFlux(isA(Long.class));
	}
	
	@Test
	void getAllMovies_exception_retryWhen() {
		
		var message = "Exception occurs in review service";
		
		Mockito.when(movieInfoService.retrieveMoviesFlux())
			.thenCallRealMethod();
		
		Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
		.thenThrow(new RuntimeException(message));
		
		
		var movieFlux = movieReactiveService.getAllMovies_retryWhen();
		
		StepVerifier.create(movieFlux)
			.expectErrorMessage(message)
			.verify();
		
		verify(reviewService,times(4)).retrieveReviewsFlux(isA(Long.class));
	}
	
	@Test
	void getAllMovies_exception_repeat() {
		
		var message = "Exception occurs in review service";
		
		Mockito.when(movieInfoService.retrieveMoviesFlux())
			.thenCallRealMethod();
		
		Mockito.when(reviewService.retrieveReviewsFlux(anyLong()))
		.thenCallRealMethod();
		
		
		var movieFlux = movieReactiveService.getAllMovies_repeat();
		
		StepVerifier.create(movieFlux)
			.expectNextCount(6)
			.thenCancel()
			.verify();
		
		verify(reviewService,times(6)).retrieveReviewsFlux(isA(Long.class));
	}
	
}

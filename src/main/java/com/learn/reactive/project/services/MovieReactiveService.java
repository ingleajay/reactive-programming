package com.learn.reactive.project.services;

import java.time.Duration;
import java.util.List;

import com.learn.reactive.project.exception.MovieException;
import com.learn.reactive.project.model.Movie;
import com.learn.reactive.project.model.Review;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

public class MovieReactiveService {

	public MovieInfoService movieInfoService;
	public ReviewService reviewService;
	public RevenueService revenueService;
	
	public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService,
			RevenueService revenueService) {
		super();
		this.movieInfoService = movieInfoService;
		this.reviewService = reviewService;
		this.revenueService = revenueService;
	}
	
	public Flux<Movie> getAllMovies(){
		
		var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
		
		return movieInfoFlux
				.flatMap(movieInfo->{
					Mono<List<Review>> reviewMono = reviewService
							.retrieveReviewsFlux(movieInfo.getMovieInfoId())
							.collectList();
					return reviewMono.map((reviewList)->new Movie(movieInfo, reviewList));
		})
		.onErrorMap((ex) -> {
                    System.out.println("Exception is " + ex);
                    throw new MovieException(ex.getMessage());
        })
		.log();
	}
	
	// retry exception given no of times and pause it and then return 
	public Flux<Movie> getAllMovies_retry(){
		
		var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
		
		return movieInfoFlux
				.flatMap(movieInfo->{
					Mono<List<Review>> reviewMono = reviewService
							.retrieveReviewsFlux(movieInfo.getMovieInfoId())
							.collectList();
					return reviewMono.map((reviewList)->new Movie(movieInfo, reviewList));
		})
		.onErrorMap((ex) -> {
                    System.out.println("Exception is " + ex);
                    throw new MovieException(ex.getMessage());
        })
		.retry(3)
		.log();
	}
	
	// retrywhen it takes time to be executed and then retuen 
	public Flux<Movie> getAllMovies_retryWhen(){
			
			var retryWhen = Retry.backoff(3,Duration.ofMillis(500))
			.onRetryExhaustedThrow((retryBackoff,retrySignal)->
				Exceptions.propagate(retrySignal.failure()
			));
			
			var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
			
			return movieInfoFlux
					.flatMap(movieInfo->{
						Mono<List<Review>> reviewMono = reviewService
								.retrieveReviewsFlux(movieInfo.getMovieInfoId())
								.collectList();
						return reviewMono.map((reviewList)->new Movie(movieInfo, reviewList));
			})
			.onErrorMap((ex) -> {
	                    System.out.println("Exception is " + ex);
	                    throw new MovieException(ex.getMessage());
	        })
			.retryWhen(retryWhen)
			.log();
		}
		
	public Flux<Movie> getAllMovies_repeat(){
			
			var retryWhen = Retry.backoff(3,Duration.ofMillis(500))
			.onRetryExhaustedThrow((retryBackoff,retrySignal)->
				Exceptions.propagate(retrySignal.failure()
			));
			
			var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
			
			return movieInfoFlux
					.flatMap(movieInfo->{
						Mono<List<Review>> reviewMono = reviewService
								.retrieveReviewsFlux(movieInfo.getMovieInfoId())
								.collectList();
						return reviewMono.map((reviewList)->new Movie(movieInfo, reviewList));
			})
			.onErrorMap((ex) -> {
	                    System.out.println("Exception is " + ex);
	                    throw new MovieException(ex.getMessage());
	        })
			.retryWhen(retryWhen)
			.repeat()
			.log();
		}		
	
	public Mono<Movie> getMovieById(long movieId){
		
		// one way
//		var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
//		return movieInfoMono
//				.flatMap(movieInfo->{
//					Mono<List<Review>> reviewMono = reviewService
//							.retrieveReviewsFlux(movieInfo.getMovieInfoId())
//							.collectList();
//					return reviewMono.map((reviewList)->new Movie(movieInfo, reviewList));
//		}).log();
		
		// second way
		var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
		var reviewList = reviewService.retrieveReviewsFlux(movieId).collectList();
		
		return movieInfoMono.zipWith(reviewList,
				(movieInfo,reviewInfo)->new Movie(movieInfo,reviewInfo));
	}

	public Mono<Movie> getMovieById_withRevenue(long movieId) {

        var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        var reviewList = reviewService.retrieveReviewsFlux(movieId)
                .collectList();
        var revenueMono = Mono.fromCallable(() -> revenueService.getRevenue(movieId))
                .subscribeOn(Schedulers.boundedElastic());

        return movieInfoMono.zipWith(reviewList, (movieInfo, reviews) -> new Movie(movieInfo, reviews))
                .zipWith(revenueMono, ((movie, revenue) -> {
                    movie.setRevenue(revenue);
                    return movie;
                })).log();
    }
}

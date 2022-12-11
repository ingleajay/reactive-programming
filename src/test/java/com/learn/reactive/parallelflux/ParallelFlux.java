package com.learn.reactive.parallelflux;

import org.junit.jupiter.api.Test;

import com.learn.reactive.ParallelFluxEx;

import reactor.test.StepVerifier;

public class ParallelFlux {
	
	@Test
	void explore_parallel() {
		
		var flux = ParallelFluxEx.explore_parallel();
		
		StepVerifier.create(flux)
			.expectNextCount(3)
			.verifyComplete();
	}
	
	@Test
	void explore_parallel_flux() {
		
		var flux = ParallelFluxEx.explore_parallel_flux();
		
		StepVerifier.create(flux)
			.expectNextCount(6)
			.verifyComplete();
	}
	
	@Test
	void explore_parallel_flux_seq() {
		
		var flux = ParallelFluxEx.explore_parallel_flux_seq();
		
		StepVerifier.create(flux)
			.expectNextCount(3)
			.verifyComplete();
	}
}

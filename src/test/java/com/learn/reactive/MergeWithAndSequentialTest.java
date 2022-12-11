package com.learn.reactive;

import org.junit.jupiter.api.Test;

import com.learn.reactive.opearator.MergeAndMergeWith;

import reactor.test.StepVerifier;

public class MergeWithAndSequentialTest {

	MergeAndMergeWith m = new MergeAndMergeWith();
	
	@Test
	public void mergeWithTest() {
		var g = m.mergeWithFlux();
		StepVerifier
			.create(g)
			.expectNext("A","D","B","E","C","F")
			.verifyComplete();
	}
	
	@Test
	public void mergeSeqTest() {
		var g = m.mergeSequentialFlux();
		StepVerifier
			.create(g)
			.expectNext("A","B","C","D","E","F")
			.verifyComplete();
	}
}

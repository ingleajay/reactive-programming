package com.learn.reactive;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class AppTest
{
    App a = new App();
    
    @Test
    void testFlux() {
    	var g = a.integerFlux();
    	StepVerifier.create(g)
    		.expectNextCount(5)
    		.expectNext(1,2,3,4,5)
    		.verifyComplete();
    }
}

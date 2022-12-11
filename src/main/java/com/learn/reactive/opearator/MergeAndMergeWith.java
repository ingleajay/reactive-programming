package com.learn.reactive.opearator;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MergeAndMergeWith {

	public static void main(String[] args) {
		
		// mergewith is not in sequence 
		
		merge().subscribe(i->System.out.println(i));
		System.out.println("++++++++++++++++");
		mergeWithFlux().subscribe();
		System.out.println("++++++++++++++++");
		mergeWithMono().subscribe(i->System.out.println(i));
		
		// if u want in seq u can used mergeSequential
		mergeSequentialFlux().subscribe(i->System.out.println(i));
	}


	public static Flux<String> merge(){
		
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		
		return Flux.merge(a,b);
	}
	
	public static Flux<String> mergeWithFlux(){
	
		var a = Flux.just("A","B","C").delayElements(Duration.ofMillis(100));
		var b = Flux.just("D","E","F").delayElements(Duration.ofMillis(124));
		
		return a.mergeWith(b).log();
	}
	
	public static Flux<String> mergeSequentialFlux(){
		
		var a = Flux.just("A","B","C").delayElements(Duration.ofMillis(100));
		var b = Flux.just("D","E","F").delayElements(Duration.ofMillis(124));
		
		return Flux.mergeSequential(a,b).log();
	}

	public static Flux<String> mergeWithMono(){
		
		var a = Mono.just("A");
		var b = Mono.just("B");
		var c = Mono.just("D");
		
		return a.mergeWith(b).concatWith(c);
	}
}

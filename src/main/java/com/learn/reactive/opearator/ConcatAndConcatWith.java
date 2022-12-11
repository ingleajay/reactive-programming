package com.learn.reactive.opearator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ConcatAndConcatWith {

	public static void main(String[] args) {
		// it is in sequence

		concat().subscribe((i)->System.out.println(i));
		System.out.println("++++++++++++++++++");
		
		concatWithFlux().subscribe((i)->System.out.println(i));
		System.out.println("++++++++++++++++++");
		
		concatWithMono().subscribe((i)->System.out.println(i));
	}
	
	public static Flux<String> concat(){
		
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		
		return Flux.concat(a,b);
	}
	
	public static Flux<String> concatWithFlux(){
	
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		
		return a.concatWith(b);
	}

	public static Flux<String> concatWithMono(){
		
		var a = Mono.just("A");
		var b = Mono.just("B");
		var c = Mono.just("D");
		
		return a.concatWith(b).concatWith(c);
	}
}

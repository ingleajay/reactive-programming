package com.learn.reactive.opearator;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ZipAndZipWith {

	public static void main(String[] args) {
		
		// zip adds data from multiple flux can be add upto 8 flux at time
		
		zip().subscribe((i)->System.out.println(i));
		System.out.println("++++++++++++++++");
		
		zipWithFlux().subscribe(j->System.out.println(j));
		System.out.println("++++++++++++");
		
		zipWithMono().subscribe(j->System.out.println(j));
		System.out.println("++++++++++++");
		
		zipWithMoreFlux().subscribe(j->System.out.println(j));
	}
	
	public static Flux<String> zip(){
		
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		
		return Flux.zip(a,b,(p,q)->p+q);
	}
	
	public static Flux<String> zipWithFlux(){
		
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		
		return a.zipWith(b,(c,d)->c+d).log();
	}
	
	public static Flux<String> zipWithMoreFlux(){
		
		var a = Flux.just("A","B","C");
		var b = Flux.just("D","E","F");
		var c = Flux.just("1","2","3");
		var d = Flux.just("4","5","6");
		
		return Flux.zip(a,b,c,d)
				.map(t ->t.getT1()+t.getT2()+t.getT3()+t.getT4())
				.log();
	}
	
	
	
	public static Mono<String> zipWithMono(){
		
		var a = Mono.just("A");
		var b = Mono.just("B");
		
		return a.zipWith(b)
				.map(t -> t.getT1() + t.getT2())
				.log();
	}

}

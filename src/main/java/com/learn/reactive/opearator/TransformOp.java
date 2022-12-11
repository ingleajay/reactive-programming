package com.learn.reactive.opearator;

import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class TransformOp {

	public static void main(String[] args) {
	
		// when you want similar kind of dunctionaity in code multiple time use transform
		
		transformFlux().subscribe();

	}
	
	public static Flux<String> transformFlux(){
		
		Function<Flux<String>, Flux<String>> func = name -> name.map(String :: toUpperCase)
				.filter(s -> s.length() != 4);
		
		return Flux.fromIterable(List.of("Ajay","Vijay","Sonali","djdjff"))
				.transform(func)
				.flatMap(s->splitToChar(s))
				.log();
				
	}
	
	public static Flux<String> splitToChar(String i) {
		var k = i.split("");
		return Flux.fromArray(k);
	}

}

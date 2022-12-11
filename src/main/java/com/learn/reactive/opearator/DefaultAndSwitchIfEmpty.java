package com.learn.reactive.opearator;

import java.util.List;
import java.util.function.Function;

import reactor.core.publisher.Flux;

public class DefaultAndSwitchIfEmpty {

	public static void main(String[] args) {
		
		// if value is not match then by default value will be in next function
		
		//transformFluxDeafultEmpty().subscribe();
		transformFluxSwitchIfEmpty().subscribe();
	}
	
	public static Flux<String> transformFluxSwitchIfEmpty(){
		
		Function<Flux<String>, Flux<String>> func = name -> name.map(String :: toUpperCase)
				.filter(s -> s.length() != 4)
				.flatMap(s->splitToChar(s));
		
		var defaultFlux = Flux.just("default")
				.transform(func);
		
		return Flux.fromIterable(List.of("Ajay"))
				.transform(func)
				.switchIfEmpty(defaultFlux)
				.log();
				
	}
	
	

	public static Flux<String> transformFluxDeafultEmpty(){
		
		Function<Flux<String>, Flux<String>> func = name -> name.map(String :: toUpperCase)
				.filter(s -> s.length() != 4);
		
		return Flux.fromIterable(List.of("Ajay"))
				.transform(func)
				.flatMap(s->splitToChar(s))
				.defaultIfEmpty("Default")
				.log();
				
	}
	
	public static Flux<String> splitToChar(String i) {
		var k = i.split("");
		return Flux.fromArray(k);
	}
}

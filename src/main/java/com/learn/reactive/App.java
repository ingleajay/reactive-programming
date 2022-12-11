package com.learn.reactive;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class App 
{
    public static void main( String[] args )
    {
    	// first Flux 
    	integerFlux().subscribe((i)->{System.out.println(i);});
    	
    	// first mono
    	StringMono().subscribe((name)->{System.out.println(name);});
    	
    	// get to konw each steps of flux
    	integerFluxLog().subscribe((i)->{System.out.println(i);});
    }
    
    public static Flux<Integer> integerFlux() {
    	return Flux.fromIterable(List.of(1,2,3,4,5));
    }
    
    public static Mono<String> StringMono(){
    	return Mono.just("Ajay Ingle");
    }
    
    public static Flux<Integer> integerFluxLog() {
    	return Flux.fromIterable(List.of(1,2,3,4,5)).log();
    }
}

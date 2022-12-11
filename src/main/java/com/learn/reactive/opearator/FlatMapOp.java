package com.learn.reactive.opearator;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FlatMapOp {

	public static void main(String[] args) {
		
		// It's used when we call rest api async way 
		
		// use flat map on flux
		var j = StringFlux().flatMap((i)->splitToChar(i));
		j.subscribe((ch)->System.out.println(ch));
		System.out.println("Flux--++++++++++++++++++++++++++++++++++");
		
		// asyn way to do flat map on flux
		var j1 = StringFlux().flatMap((i)->splitToCharAsyncWay(i)).log().blockLast();
		//j1.subscribe((ch)->System.out.println(ch)); = you can not use like this 
		
		System.out.println("Mono--++++++++++++++++++++++++++++++++++");
		// use flat map on mono
		var j2 = StringMono().flatMap((i)->mono_splitToChar(i));
		j2.subscribe((ch)->System.out.println(ch));
		
		
		System.out.println("Flat Map Many--++++++++++++++++++++++++++++++++++");
		// use flat map many if have list of string in mono then used
		var j3 = StringMono().flatMapMany((i)->splitToChar(i)).log();
		j3.subscribe((ch)->System.out.println(ch));
	}
	
	public static Mono<List<String>> mono_splitToChar(String i) {
		var k = i.split("");
		var ch= List.of(k);
		return Mono.just(ch);
	}
	

	public static Mono<String> StringMono() {
		return Mono.just("Manohar");
	}
	
	
	public static Flux<String> splitToChar(String i) {
		var k = i.split("");
		return Flux.fromArray(k);
	}
	
	public static Flux<String> splitToCharAsyncWay(String i) {
		var delay = new Random().nextInt(1000);
		var k = i.split("");
		return Flux.fromArray(k).delayElements(Duration.ofMillis(delay));
	}

	public static Flux<String> StringFlux() {
		return Flux.fromIterable(List.of("Ajay","Vijay"));
	}

}

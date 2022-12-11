package com.learn.reactive.exception;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxWithException {

	public static void main(String[] args) {
		
		exception_flux().subscribe();
		System.out.println("+++++++++++++++++");
		exception_onerrorReturn().subscribe();
		System.out.println("+++++++++++++++++");
		explore_OnErrorResume(new IllegalStateException()).subscribe();
		System.out.println("+++++++++++++++++");
		explore_OnErrorContinue().subscribe();
		System.out.println("+++++++++++++++++");
		explore_OnErrorMap().subscribe();
		System.out.println("+++++++++++++++++");
		explore_doOnError(new IllegalStateException()).subscribe();
		System.out.println("+++++++++++++++++");
		exception_mono_exception().subscribe();
	}
	
	//you will throw exception
	public static Flux<String> exception_flux(){
		return Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception occured")))
				.concatWith(Flux.just("D"))
				.log();
	}
	
	// you will handle exception by adding default value after it
	public static Flux<String> exception_onerrorReturn(){
		return Flux.just("A","B","C")
				.concatWith(Flux.error(new RuntimeException("Exception occured")))
				.onErrorReturn("D")
				.log();
	}
	
	// you will handle exception and resume it and add it back other flux
	 public static Flux<String> explore_OnErrorResume(Exception e) {

	        var recoveryFlux = Flux.just("D", "E", "F");

	        var flux = Flux.just("A", "B", "C")
	                .concatWith(Flux.error(e))
	                .onErrorResume((exception) -> {
	                    System.out.println("Exception  : " + exception);
	                    if (exception instanceof IllegalStateException)
	                        return recoveryFlux;
	                    else
	                        return Flux.error(exception);
	                }).log();

	        return flux;

	  }
	 
	 // if you got exception in between and your ahead flux will be add using onerror continue
	 public static Flux<String> explore_OnErrorContinue() {
	        var flux = Flux.just("A", "B", "C")
	                .map(name -> {
	                    if (name.equals("B")) {
	                        throw new IllegalStateException("Exception Occurred");
	                    }
	                    return name;
	                })
	                .concatWith(Flux.just("D"))
	                .onErrorContinue((exception, value) -> {
	                    System.out.println("Value is : " + value);
	                    System.out.println("Exception is : " + exception.getMessage());
	                }).log();
	        return flux;

	 }
	 
	 // onerrormap is used to map or change type exception from one to other and it is not recover ahead ele
	 public static Flux<String> explore_OnErrorMap() {
	        var flux = Flux.just("A", "B", "C")
	                .map(name -> {
	                    if (name.equals("B")) {
	                        throw new IllegalStateException("Exception Occurred");
	                    }
	                    return name;
	                })
	                .onErrorMap((exception) -> {
	                    // log.error("Exception is : " , exception);
	                    // difference between errorResume and this one is that you dont need to add
	                    // Flux.error() to throw the exception
	                    return new ReactorException(exception, exception.getMessage());
	                }).log();

	        return flux;

	  }
	 
	 // it is piplline throw exception
	 public static Flux<String> explore_doOnError(Exception e) {

	        var flux = Flux.just("A", "B", "C")
	                .concatWith(Flux.error(e))
	                .doOnError((exception) -> {
	                    System.out.println("Exception is : " + e);
	                    //Write any logic you would like to perform when an exception happens
	                }).log();

	        return flux;

	    }
	 

	    public static Mono<Object> exception_mono_exception() {

	        var mono = Mono.just("B")
	                .map(value -> {
	                    throw new RuntimeException("Exception Occurred");
	                })
	                .onErrorReturn("abc")
	                .log();
	        return mono;

	    }
}

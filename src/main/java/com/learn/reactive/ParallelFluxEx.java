package com.learn.reactive;

import java.util.List;
import java.util.concurrent.Delayed;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import static com.learn.reactive.CommonUtil.delay;

public class ParallelFluxEx {
	
	
	static List<String> namesList = List.of("alex", "ben", "chloe");
	static List<String> namesList1 = List.of("adam", "jill", "jack");

	public static ParallelFlux<String> explore_parallel(){
		var noOfCores = Runtime.getRuntime().availableProcessors();
		System.out.println("noOfCores : " + noOfCores);
		return Flux.fromIterable(namesList)
				//.publishOn(Schedulers.parallel()) // it is not dividing flux request in task
				.parallel()
				.runOn(Schedulers.parallel())
				.map(i->upperCase(i))
				.log();
	}
	
	public static Flux<String> explore_parallel_flux() {
        var namesFlux = Flux.fromIterable(namesList)
        		.flatMap(name->Mono.just(name))
                .map(i -> upperCase(i))
                .subscribeOn(Schedulers.parallel())
                .log();

        var namesFlux1 = Flux.fromIterable(namesList1)
        		.flatMap(name -> Mono.just(name))
                .map(i->upperCase(i))
                .subscribeOn(Schedulers.parallel())
                .map((s) -> {
                    return s;
                })
                .log();

        return namesFlux.mergeWith(namesFlux1);
    }
	
	// onseq - all ele
	public static Flux<String> explore_parallel_flux_seq() {
        var namesFlux = Flux.fromIterable(namesList)
        		.flatMap(name->Mono.just(name))
                .map(i -> upperCase(i))
                .subscribeOn(Schedulers.parallel())
                .log();
		return namesFlux;
	}
	
	private static String upperCase(String name) {
        delay(1000);
        return name.toUpperCase();
    }
}

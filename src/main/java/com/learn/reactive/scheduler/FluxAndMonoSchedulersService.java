package com.learn.reactive.scheduler;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import static com.learn.reactive.CommonUtil.delay;

public class FluxAndMonoSchedulersService {

	static List<String> namesList = List.of("alex", "ben", "chloe");
    static List<String> namesList1 = List.of("adam", "jill", "jack");

    // used to switch your thread to parallel and bound elastic thread for async
    public Flux<String> explore_publishOn() {
        // start without publish on
        // add publishon Schedulers.parallel()
        // add publishon Schedulers.boundedElastic() for the second flux


        var namesFlux = Flux.fromIterable(namesList)
                .publishOn(Schedulers.parallel())
                .map(this::upperCase)
                .log();

        var namesFlux1 = Flux.fromIterable(namesList1)
                .publishOn(Schedulers.boundedElastic())
                .map(this::upperCase)
                .map((s) -> {
                    return s;
                })
                .log();

        return namesFlux.mergeWith(namesFlux1);
    }
    
    public Flux<String> explore_subscribeOn() {
        var namesFlux = flux1()
                .map((s) -> {
                    return s;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .log();

        var namesFlux1 = flux2()
                .map((s) -> {
                    return s;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map((s) -> {
                    return s;
                })
                .log();

        return namesFlux.mergeWith(namesFlux1);
    }
    
    public Flux<String> flux1() {
        var namesFlux = Flux.fromIterable(namesList)
                .map(this::upperCase);
        return namesFlux;
    }

    public Flux<String> flux2() {
        var namesFlux = Flux.fromIterable(namesList1)
                .map(this::upperCase);
        return namesFlux;
    }
    
    private String upperCase(String name) {
        delay(1000);
        return name.toUpperCase();
    }
}

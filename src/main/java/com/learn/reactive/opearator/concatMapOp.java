package com.learn.reactive.opearator;

public class concatMapOp {

	public static void main(String[] args) {
		
		// it's preverse order for map 
		// it takes lot of time for doing this rather than flatmap.
		// flatmap doesn't maintaine order
		
		var j1 = FlatMapOp.StringFlux().concatMap(
				(i)->FlatMapOp.splitToCharAsyncWay(i)).log().blockLast();
	}

}

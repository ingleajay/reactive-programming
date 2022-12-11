package com.learn.reactive.opearator;

import com.learn.reactive.App;

public class MapOp {

	public static void main(String[] args) {
			
		App a = new App();
		
		
		// map on flux
		var t = a.integerFlux().map((i)->i*2);
		t.subscribe((j)->System.out.println(j));
		
		// map on mono
		var y = a.StringMono().map(String::toUpperCase);
		y.subscribe((name)->{System.out.println(name);});
	}

}

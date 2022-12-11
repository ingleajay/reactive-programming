package com.learn.reactive.opearator;

import com.learn.reactive.App;

public class FliterOp {

	public static void main(String[] args) {
			
		App a = new App();
		
		
		// fliter on flux
		var t = a.integerFlux().filter((i)->i%2==0);
		t.subscribe((j)->System.out.println(j));
		
		// filter on mono
		var y = a.StringMono().filter((n)->n.contains("le"));
		y.subscribe((name)->{System.out.println(name);});
		
	}

}

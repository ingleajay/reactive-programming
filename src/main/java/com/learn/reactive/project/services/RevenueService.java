package com.learn.reactive.project.services;

import com.learn.reactive.project.model.Revenue;

import static com.learn.reactive.CommonUtil.delay;


public class RevenueService {

	 public Revenue getRevenue(Long movieId){
	        delay(1000); // simulating a network call ( DB or Rest call)
	        Revenue r= new Revenue(movieId,1000000 ,5000000);
	        return r;
	 }
}

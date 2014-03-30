package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("location")
public interface LocationService extends RemoteService {
	public List<Park> getParksWithinRadius(int radius, float centerLat,
			float centerLon, List<Park> parks);
	
	public List<Restaurant> getRestaurantsWithinRadius(int radius, float centerLat,
			float centerLon, List<Restaurant> restaurants);

}
package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LocationServiceAsync {
	public void getParksWithinRadius(int radius, float centerLat,
			float centerLon, List<Park> parks, AsyncCallback<List<Park>> async);

	public void getRestaurantsWithinRadius(int radius, float centerLat,
			float centerLon, List<Restaurant> restaurants,
			AsyncCallback<List<Restaurant>> async);
}

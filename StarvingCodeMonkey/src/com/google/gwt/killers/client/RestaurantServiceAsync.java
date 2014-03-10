package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RestaurantServiceAsync {
	public void getRestaurants(AsyncCallback<List<Restaurant>> async);
}
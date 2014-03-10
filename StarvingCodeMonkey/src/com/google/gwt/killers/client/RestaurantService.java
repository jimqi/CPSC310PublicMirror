package com.google.gwt.killers.client;

import java.util.List;

import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("restaurant")
public interface RestaurantService extends RemoteService {
  public List<Restaurant> getRestaurants() throws NotLoggedInException;
  
  
}
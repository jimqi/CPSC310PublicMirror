package com.google.gwt.killers.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.killers.client.NotLoggedInException;
import com.google.gwt.killers.client.RestaurantService;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.killers.reader.RestaurantReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RestaurantServiceImpl extends RemoteServiceServlet implements
		RestaurantService {

	private static final long serialVersionUID = -4482117465037115147L;
	private static final Logger LOG = Logger.getLogger(ParkServiceImpl.class
			.getName());

	public List<Restaurant> getRestaurants() throws NotLoggedInException {
		LOG.log(Level.INFO, "Start: Loading restaurant data");
		checkLoggedIn();
		RestaurantReader reader = new RestaurantReader();
		List<Restaurant> restaurants = reader.loadData();
		LOG.log(Level.INFO, "Stop: Loading park data");
		return restaurants;
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("User is not logged in.");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
}
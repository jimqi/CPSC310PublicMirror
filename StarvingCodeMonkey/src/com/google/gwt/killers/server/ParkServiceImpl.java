package com.google.gwt.killers.server;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.killers.client.NotLoggedInException;
import com.google.gwt.killers.client.ParkService;
import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.reader.ParkReader;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ParkServiceImpl extends RemoteServiceServlet implements
		ParkService {

	private static final long serialVersionUID = -338703689199351089L;
	private static final Logger LOG = Logger.getLogger(ParkServiceImpl.class
			.getName());

	public List<Park> getParks() throws NotLoggedInException {
		LOG.log(Level.INFO, "Start: Loading park data");
		checkLoggedIn();
		ParkReader reader = new ParkReader();
		List<Park> parks = reader.loadData();
		LOG.log(Level.INFO, "Stop: Loading park data");
		return parks;
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
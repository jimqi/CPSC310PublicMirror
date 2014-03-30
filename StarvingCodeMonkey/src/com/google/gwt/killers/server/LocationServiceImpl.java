package com.google.gwt.killers.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.killers.client.LocationService;
import com.google.gwt.killers.entity.Park;
import com.google.gwt.killers.entity.Restaurant;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LocationServiceImpl extends RemoteServiceServlet implements
		LocationService {

	private static final long serialVersionUID = 2229010277568786910L;
	private static final Logger LOG = Logger
			.getLogger(LocationServiceImpl.class.getName());

	private static final double EARTH_RADIUS = 3958.75;

	private static final int MILE_TO_METER_CONVERSION = 1609;

	private static final int KM_TO_METER_CONVERSION = 1000;

	@Override
	public List<Park> getParksWithinRadius(int radius, float centerLat,
			float centerLon, List<Park> parks) {
		List<Park> parksWithin = new ArrayList<Park>();
		float radiusInMeters = radius * KM_TO_METER_CONVERSION;

		for (Park p : parks) {
			float distance = distanceBetweenInKM(centerLat, centerLon,
					p.getLatitude(), p.getLongitude());
			if (radiusInMeters >= distance) {
				LOG.log(Level.INFO, "Park " + p.getName() + " is within "
						+ radius + " KM");
				parksWithin.add(p);
			}
		}

		return parksWithin;
	}

	@Override
	public List<Restaurant> getRestaurantsWithinRadius(int radius,
			float centerLat, float centerLon, List<Restaurant> restaurants) {
		List<Restaurant> restaurantsWithin = new ArrayList<Restaurant>();
		float radiusInMeters = radius * KM_TO_METER_CONVERSION;

		for (Restaurant p : restaurants) {
			float distance = distanceBetweenInKM(centerLat, centerLon,
					p.getLatitude(), p.getLongitude());
			if (radiusInMeters >= distance) {
				LOG.log(Level.INFO, "Restaurant " + p.getName() + " is within "
						+ radius + " KM");
				restaurantsWithin.add(p);
			}
		}

		return restaurantsWithin;
	}

	private float distanceBetweenInKM(float lat1, float lng1, float lat2,
			float lng2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = EARTH_RADIUS * c;

		return (float) (dist * MILE_TO_METER_CONVERSION);
	}

}
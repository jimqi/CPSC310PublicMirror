package com.google.gwt.killers.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import au.com.bytecode.opencsv.CSVParser;

import com.google.gwt.killers.entity.Restaurant;

public class RestaurantReader {

	Logger logger = Logger.getLogger("RestaurantReaderLogger");

	private static final String Restaurant_DATA_URL = "https://dl.dropboxusercontent.com/u/69390696/new_food_vendor_locations.csv";

	private static final String Restaurant_DATA_FILENAME = "WEB-INF"
			+ File.separator + "data" + File.separator
			+ "new_food_vendor_locations.csv";

	public RestaurantReader() {
		// TODO Auto-generated constructor stub
	}

	public List<Restaurant> loadData() {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		try {
			URL dataUrl = new URL(Restaurant_DATA_URL);
			URLConnection conn = dataUrl.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			// BufferedReader in = new BufferedReader(new FileReader(
			// Restaurant_DATA_FILENAME));
			CSVParser parser = new CSVParser();

			// We ignore the first line, which has no restaurant data
			String firstLine = in.readLine();

			String inputLine;
			String name;
			float lat;
			float lon;
			String address;
			String status;
			String vendorType;
			String id;
			String food;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				String[] values = parser.parseLine(inputLine);
				if (values.length >= 7) {
					id = values[0];
					vendorType = values[1];
					status = values[2];
					name = values[3];
					address = values[4];
					food = values[5];
					// lat is at values[6] lon at values[7]
					String slat, slon;
					if (values[6].length() > 9) {
					slat = values[6].substring(0, 9);
					}
					else slat = "0";
					if (values[7].length() > 9) {
						slon = values[7].substring(0, 9);
						}
					else slon = "0";
					lat = Float.valueOf(slat);
					lon = Float.valueOf(slon);
					Restaurant p = new Restaurant(id, name, status, vendorType,
							address, lat, lon, food);
					restaurants.add(p);
				} else {
					System.err
							.println("Restaurant data contains unexpected number of values: "
									+ inputLine);
				}
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Exception: " + e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Exception: " + e.getLocalizedMessage());
		}

		return restaurants;
	}

}
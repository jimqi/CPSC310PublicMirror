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

import com.google.gwt.killers.entity.Park;

public class ParkReader {

	Logger logger = Logger.getLogger("ParkReaderLogger");

	private static final String PARK_DATA_URL = "ftp://webftp.vancouver.ca/OpenData/csv/parks.csv";

	private static final String PARK_DATA_FILENAME = "WEB-INF" + File.separator
			+ "data" + File.separator + "parks.csv";

	public ParkReader() {
		// TODO Auto-generated constructor stub
	}

	public List<Park> loadData() {
		List<Park> parks = new ArrayList<Park>();
		try {
			// URL dataUrl = new URL(PARK_DATA_URL);
			// URLConnection conn = dataUrl.openConnection();

			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// conn.getInputStream()));

			BufferedReader in = new BufferedReader(new FileReader(
					PARK_DATA_FILENAME));
			CSVParser parser = new CSVParser();

			// We ignore the first line, which has not park data
			String firstLine = in.readLine();

			String inputLine;
			Long id;
			String name;
			float lat;
			float lon;
			String address;
			String parkUrl;
			String neighbourhood;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				String[] values = parser.parseLine(inputLine);
				if (values.length >= 14) {
					id = Long.valueOf(values[0]);
					name = values[1];
					// parse out the lat/lon
					String latLon = values[7];
					String[] location = latLon.split(",");
					lat = Float.valueOf(location[0]);
					lon = Float.valueOf(location[1]);

					address = values[3] + " " + values[4];
					parkUrl = values[10];
					neighbourhood = values[9];

					Park p = new Park(id, name, lat, lon, address, parkUrl,
							neighbourhood);
					parks.add(p);
				} else {
					System.err
							.println("Park data contains unexpected number of values: "
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

		return parks;
	}

}

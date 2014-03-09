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

import au.com.bytecode.opencsv.CSVParser;

import com.google.gwt.killers.entity.Park;

public class ParkReader {

	private static final String PARK_DATA_URL = "ftp://webftp.vancouver.ca/OpenData/csv/parks.csv";

	private static final String PARK_DATA_FILENAME = "data\\parks.csv";

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
			String name;
			float lat;
			float lon;
			String address;
			String parkUrl;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				String[] values = parser.parseLine(inputLine);
				if (values.length >= 14) {
					name = values[1];
					// TODO parse out the lat/lon
					lat = 0; // Float.valueOf(values[1]);
					lon = 100; // Float.valueOf(values[2]);
					address = values[3] + " " + values[4];
					parkUrl = values[10];

					Park p = new Park(name, lat, lon, address, parkUrl);
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		return parks;
	}

}

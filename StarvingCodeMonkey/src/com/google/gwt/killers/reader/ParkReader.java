package com.google.gwt.killers.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVParser;

import com.google.gwt.killers.entity.Park;

public class ParkReader {

	private static final String PARK_DATA_URL = "ftp://webftp.vancouver.ca/OpenData/csv/parks.csv";

	public ParkReader() {
		// TODO Auto-generated constructor stub
	}

	public List<Park> loadData() {
		List<Park> parks = new ArrayList<Park>();
		try {
			URL dataUrl = new URL(PARK_DATA_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dataUrl.openStream()));
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
				System.out.println(inputLine);
				String[] values = parser.parseLine(inputLine);
				if (values.length >= 5) {
					name = values[0];
					lat = Float.valueOf(values[1]);
					lon = Float.valueOf(values[2]);
					address = values[3];
					parkUrl = values[4];

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

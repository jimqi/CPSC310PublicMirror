package com.google.gwt.killers.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.killers.entity.Park;

public class ParkReader {

	private static final String PARK_DATA_URL = "ftp://webftp.vancouver.ca/OpenData/csv/parks.csv";

	public ParkReader() {
		// TODO Auto-generated constructor stub
	}

	public void loadData() {
		List<Park> parks = new ArrayList<Park>();
		try {
			URL dataUrl = new URL(PARK_DATA_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					dataUrl.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

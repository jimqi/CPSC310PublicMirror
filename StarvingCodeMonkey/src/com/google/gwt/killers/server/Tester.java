package com.google.gwt.killers.server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Tester {

	public DataImporter trader;
	public Dataset data;

	public void JSONimport(String aurl) throws IOException, Exception,
			Throwable {
		trader = new JSONImporter();
		data = trader.importFromUrl(new URL(aurl));
	}

	public Dataset getDatase() {
		return data;
	}

	public void print() {
		data.print();
	}

	/*
	 * public static void main(String[] args) throws Exception, Throwable {
	 * Tester atest= new Tester();
	 * atest.JSONimport("your URL for dataset"
	 * ); atest.print();
	 * 
	 * }
	 */

}

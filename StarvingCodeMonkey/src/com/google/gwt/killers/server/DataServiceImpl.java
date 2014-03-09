package com.google.gwt.killers.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.killers.client.DataService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class DataServiceImpl extends RemoteServiceServlet implements
		DataService {

	public DataImporter trader;
	public Dataset data;

	public String[] getColNames(String url) {

		trader = new JSONImporter();
		try {
			data = trader.importFromUrl(new URL(url));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.getColNames();
	}

	public String[][] get2DArray(String url) {
		char[] url_char = url.toCharArray();
		if (url_char[url.length() - 1] == 'n') {
			trader = new JSONImporter();
			try {
				data = trader.importFromUrl(new URL(url));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ImportException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data.get2DArray();
		} else if (url_char[url.length() - 1] == 'l') {
			trader = new XMLImporter();
			try {
				data = trader.importFromUrl(new URL(url));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ImportException e) {
				e.printStackTrace();
			}
			return data.get2DArray();
		} else if (url_char[url.length() - 1] == 'v') {
			trader = new CSVImporter();
			try {
				data = trader.importFromUrl(new URL(url));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ImportException e) {
				e.printStackTrace();
			}
			return data.get2DArray();
		}
		return null;
	}
	/*
	 * public static void main(String[] args){ DataServiceImpl aaa = new
	 * DataServiceImpl(); String[][] rows;
	 * rows=aaa.get2DArray("your data URL"
	 * ); for(int i = 0;i<rows.length; i++) { for (int x = 0; x <
	 * rows[i].length; x++) { System.out.print(rows[1][x] + "\t"); }
	 * System.out.println(); } }
	 */
}

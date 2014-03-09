package com.google.gwt.killers.server;

/**
 * detail implementation for Importer
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gwt.killers.client.ImporterService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImporterServiceImpl extends RemoteServiceServlet implements
		ImporterService {

	DataImporter trader;
	Dataset data = new Dataset();

	public void importCSVData(String url) {
		trader = new CSVImporter();
		try {
			data = trader.importFromInputStreamReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void importJSONData(String url) {
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

	}

	public String[][] getData() {
		return data.get2DArray();
	}

	public String[] get_ColNames() {
		return data.getColNames();
	}

}

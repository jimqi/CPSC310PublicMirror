package com.google.gwt.killers.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;

public class XMLImporter extends DataImporter {

	@Override
	public Dataset importFromUrl(URL url) throws IOException, ImportException,
			JSONException {
		String input_line;
		String keeper = new String();

		URLConnection uc = url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				uc.getInputStream()));

		while ((input_line = br.readLine()) != null) {
			keeper += input_line;
		}
		br.close();

		JSONObject jo = XML.toJSONObject(keeper);
		jo = (JSONObject) jo.get(JSONObject.getNames(jo)[0]);
		JSONArray ja = jo.getJSONArray(JSONObject.getNames(jo)[0]);

		Dataset ds = new Dataset();

		// if JSON array only contains one element, it is probably an array in
		// an array
		String[] keys;
		try {
			if (ja.length() == 1)
				ja = ja.getJSONArray(0);

			keys = JSONObject.getNames(ja.getJSONObject(0));
			ds.setColumnNames(keys);
		} catch (JSONException e1) {
			// can fail when array doesn't contain JSON objects
			throw new ImportException("JSON array doesn't contain JSON objects");
		}

		for (int i = 0; i < ja.length(); i++) {
			JSONObject map;
			try {
				map = ja.getJSONObject(i);
			} catch (JSONException e) {
				throw new ImportException(
						"JSON array doesn't contain JSON objects");
			}
			Object[] row = new Object[keys.length];
			if (keys.length != map.length())
				throw new ImportException(
						"Inconsistent numbers of key-value pairs per object.");
			int j = 0;
			for (String key : keys) {
				try {
					row[j] = map.getString(key);
					// row[j] = map.getDouble(key);
				} catch (JSONException e) {
					try {
						row[j] = map.get(key);
					} catch (JSONException e1) {
						row[j] = null;
					}
				}
				j++;
			}
			ds.addRow(row);
		}

		ds.finishPopulationAndComputeSchema();
		return ds;
	}

	@Override
	public Dataset importFromInputStreamReader(InputStreamReader reader)
			throws IOException, ImportException {

		String xml = IOUtils.toString(reader);

		// System.out.println(xml);
		JSONArray ja;
		try {
			// XMLTokener token = new XMLTokener(xml); //needs to pass on some
			// string of JSON
			ja = JSONML.toJSONArray(xml);
		} catch (JSONException ex) {
			return null;
		}
		Dataset ds = new Dataset();

		// if JSON array only contains one element, it is probably an array in
		// an array
		String[] keys;
		try {
			if (ja.length() == 1)
				ja = ja.getJSONArray(0);

			keys = JSONObject.getNames(ja.getJSONObject(0));
			ds.setColumnNames(keys);
		} catch (JSONException e1) {
			// can fail when array doesn't contain JSON objects
			throw new ImportException("JSON array doesn't contain JSON objects");
		}

		for (int i = 0; i < ja.length(); i++) {
			JSONObject map;
			try {
				map = ja.getJSONObject(i);
			} catch (JSONException e) {
				throw new ImportException(
						"JSON array doesn't contain JSON objects");
			}
			Object[] row = new Object[keys.length];
			if (keys.length != map.length())
				throw new ImportException(
						"Inconsistent numbers of key-value pairs per object.");
			int j = 0;
			for (String key : keys) {
				try {
					row[j] = map.getString(key);
					// row[j] = map.getDouble(key);
				} catch (JSONException e) {
					try {
						row[j] = map.get(key);
					} catch (JSONException e1) {
						row[j] = null;
					}
				}
				j++;
			}
			ds.addRow(row);
		}

		ds.finishPopulationAndComputeSchema();
		return ds;
	}

}

package com.google.gwt.killers.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class JSONImporter extends DataImporter {

	private final Pattern numberRegex = Pattern
			.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$");

	@Override
	public Dataset importFromUrl(URL url) throws IOException, ImportException {
		InputStreamReader reader = new InputStreamReader(url.openStream());
		return importFromInputStreamReader(reader);
	}

	@Override
	public Dataset importFromInputStreamReader(InputStreamReader reader)
			throws ImportException {
		// input initial JSON array
		JSONArray ja;
		try {
			JSONTokener tokener = new JSONTokener(reader);
			ja = new JSONArray(tokener);
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

package com.google.gwt.killers.server;

/**
 * check if data is CSV file, if so, import it from url
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSVImporter extends DataImporter {

	public CSVImporter() {

	}

	// regular expression source:
	// http://www.regular-expressions.info/floatingpoint.html
	private final Pattern numberRegex = Pattern
			.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$");

	@Override
	public Dataset importFromUrl(URL url) throws IOException {
		return importFromInputStreamReader(new InputStreamReader(
				url.openStream()));
	}

	@Override
	public Dataset importFromInputStreamReader(InputStreamReader reader)
			throws IOException {
		CSVReader rd = new CSVReader(reader);
		List<String[]> myEntries = rd.readAll();
		Dataset data = new Dataset();
		int prevNumCols = -1;

		data.setColumnNames(myEntries.get(0));
		for (int i = 1; i < myEntries.size(); i++) {
			String[] rowstr = myEntries.get(i);
			if (prevNumCols != -1 && prevNumCols != rowstr.length)
				break;
			Object[] rowobj = new Object[rowstr.length];
			for (int j = 0; j < rowstr.length; j++) {
				if (numberRegex.matcher(rowstr[j]).matches()) {
					try {
						rowobj[j] = Double.parseDouble(rowstr[j]);
					} catch (NumberFormatException ex) {
						rowobj[j] = rowstr[j];
					}
				} else
					rowobj[j] = rowstr[j];
			}
			data.addRow(rowobj);
			prevNumCols = rowobj.length;
		}

		data.finishPopulationAndComputeSchema();
		return data;
	}
}

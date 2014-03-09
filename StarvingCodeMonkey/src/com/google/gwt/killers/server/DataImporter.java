package com.google.gwt.killers.server;

/**
 * data importer abstract class for all different kinds of data
 */
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public abstract class DataImporter {

	public enum Format {
		CSV, JSON, XMLATTRIB, XMLNODES
	}

	public abstract Dataset importFromUrl(URL url) throws IOException,
			ImportException, JSONException;

	public abstract Dataset importFromInputStreamReader(InputStreamReader reader)
			throws IOException, ImportException;

	public static Dataset detectFormatAndImportFromUrl(URL url) {
		// TODO: implement this if necessary
		return null;
	}

	public static Dataset importSpecificFormatFromUrl(URL url, Format fmt) {
		return null;
	}

	public static String getFormatName(Format fmt) {
		switch (fmt) {
		case CSV:
			return "CSV, standard";
		case JSON:
			return "JSON, standard";
		case XMLATTRIB:
			return "XML with data in attributes";
		case XMLNODES:
			return "XML with data for each datum in subnodes";
		}
		return "UNIMPLEMENTED";
	}

}

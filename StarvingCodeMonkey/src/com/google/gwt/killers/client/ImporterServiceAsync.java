package com.google.gwt.killers.client;

/**
 * async call back for Importer
 */

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImporterServiceAsync {

	void importJSONData(String url, AsyncCallback<Void> callback);

	void importCSVData(String url, AsyncCallback<Void> callback);

	void getData(AsyncCallback<String[][]> callback);

	void get_ColNames(AsyncCallback<String[]> callback);

}

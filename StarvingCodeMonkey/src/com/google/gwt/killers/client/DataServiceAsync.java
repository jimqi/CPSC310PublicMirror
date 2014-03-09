package com.google.gwt.killers.client;

/**
 * rpc call uses for store data on to server
 */

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
	public void getColNames(String url, AsyncCallback<String[]> callback);

	public void get2DArray(String url, AsyncCallback<String[][]> callback);
}
